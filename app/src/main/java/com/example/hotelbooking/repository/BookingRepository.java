package com.example.hotelbooking.repository;

import com.example.hotelbooking.models.Booking;

import java.util.List;

public interface BookingRepository {

    /** result is null when the chosen room type is sold out. */
    interface BookingCallback { void onResult(Booking booking); }
    interface BookingListCallback { void onResult(List<Booking> bookings); }
    interface SimpleCallback { void onComplete(); }

    void addBooking(Booking booking, String userEmail, BookingCallback callback);
    void getBookingsForUser(String userEmail, BookingListCallback callback);
    void getAllBookings(BookingListCallback callback);
    void cancelBooking(int bookingId, SimpleCallback callback);
}
