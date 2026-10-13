package com.example.hotelbooking.repository;

import com.example.hotelbooking.db.AppDatabase;
import com.example.hotelbooking.db.BookingEntity;
import com.example.hotelbooking.db.HotelEntity;
import com.example.hotelbooking.models.Booking;
import com.example.hotelbooking.models.RoomType;
import com.example.hotelbooking.util.AppExecutors;
import com.example.hotelbooking.util.RoomTypeCodec;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BookingRepositoryImpl implements BookingRepository {

    private final AppDatabase db;

    public BookingRepositoryImpl(AppDatabase db) {
        this.db = db;
    }

    @Override
    public void addBooking(Booking booking, String userEmail, BookingCallback callback) {
        AppExecutors.diskIO(() -> {
            HotelEntity hotel = db.hotelDao().getById(booking.getHotelId());
            // Reserve one room of the chosen type; fails if sold out.
            boolean reserved = hotel != null && adjustAvailability(hotel, booking.getRoomType(), -1);
            if (!reserved) {
                AppExecutors.mainThread(() -> callback.onResult(null));
                return;
            }
            BookingEntity entity = new BookingEntity(0, booking.getHotelId(), booking.getHotelName(),
                    booking.getRoomType(), booking.getCheckIn(), booking.getCheckOut(), booking.getGuests(),
                    booking.getTotalPrice(), booking.getStatus(), userEmail,
                    booking.getPaymentMethod(), booking.getPaymentStatus());
            long newId = db.bookingDao().insert(entity);
            booking.setId((int) newId);
            AppExecutors.mainThread(() -> callback.onResult(booking));
        });
    }

    @Override
    public void getBookingsForUser(String userEmail, BookingListCallback callback) {
        AppExecutors.diskIO(() -> {
            List<Booking> bookings = mapAll(db.bookingDao().getForUser(userEmail));
            AppExecutors.mainThread(() -> callback.onResult(bookings));
        });
    }

    @Override
    public void getAllBookings(BookingListCallback callback) {
        AppExecutors.diskIO(() -> {
            List<Booking> bookings = mapAll(db.bookingDao().getAll());
            AppExecutors.mainThread(() -> callback.onResult(bookings));
        });
    }

    @Override
    public void cancelBooking(int bookingId, SimpleCallback callback) {
        AppExecutors.diskIO(() -> {
            BookingEntity entity = db.bookingDao().getById(bookingId);
            if (entity != null && "Upcoming".equals(entity.status)) {
                entity.status = "Cancelled";
                if ("Paid".equals(entity.paymentStatus)) entity.paymentStatus = "Refund pending";
                db.bookingDao().update(entity);
                // Give the room back so others can book it.
                HotelEntity hotel = db.hotelDao().getById(entity.hotelId);
                if (hotel != null) adjustAvailability(hotel, entity.roomType, +1);
            }
            AppExecutors.mainThread(callback::onComplete);
        });
    }

    /** Adds delta (+1 / -1) to the room count of one room type. Returns false if it can't be applied. */
    private boolean adjustAvailability(HotelEntity hotel, String roomName, int delta) {
        List<RoomType> updated = new ArrayList<>();
        boolean found = false;
        for (RoomType rt : RoomTypeCodec.decode(hotel.roomTypesRaw)) {
            if (rt.getName().equals(roomName)) {
                int newAvailable = rt.getAvailable() + delta;
                if (newAvailable < 0) return false;
                updated.add(new RoomType(rt.getName(), rt.getPricePerNight(), newAvailable));
                found = true;
            } else {
                updated.add(rt);
            }
        }
        if (!found) return false;
        hotel.roomTypesRaw = RoomTypeCodec.encode(updated);
        db.hotelDao().update(hotel);
        return true;
    }

    private static List<Booking> mapAll(List<BookingEntity> entities) {
        List<Booking> list = new ArrayList<>();
        for (BookingEntity e : entities) {
            String status = e.status;
            // An upcoming booking whose check-out date has passed is shown as Completed.
            if ("Upcoming".equals(status) && isPast(e.checkOut)) status = "Completed";
            Booking b = new Booking(e.id, e.hotelId, e.hotelName, e.roomType, e.checkIn, e.checkOut,
                    e.guests, e.totalPrice, status, e.paymentMethod, e.paymentStatus);
            b.setUserEmail(e.userEmail);
            list.add(b);
        }
        return list;
    }

    private static boolean isPast(String date) {
        try {
            Date d = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(date);
            return d != null && d.before(new Date());
        } catch (ParseException e) {
            return false;
        }
    }
}
