package com.example.hotelbooking.repository;

import com.example.hotelbooking.data.SeedData;
import com.example.hotelbooking.db.AppDatabase;
import com.example.hotelbooking.db.HotelEntity;
import com.example.hotelbooking.models.Hotel;
import com.example.hotelbooking.util.AppExecutors;
import com.example.hotelbooking.util.RoomTypeCodec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HotelRepositoryImpl implements HotelRepository {

    private final AppDatabase db;

    public HotelRepositoryImpl(AppDatabase db) {
        this.db = db;
    }

    private void ensureSeeded() {
        if (db.hotelDao().count() < SeedData.getHotels().size()) {
            List<HotelEntity> entities = new ArrayList<>();
            for (Hotel hotel : SeedData.getHotels()) entities.add(toEntity(hotel));
            db.hotelDao().insertAll(entities);
        }
    }

    private static List<Hotel> mapAll(List<HotelEntity> entities) {
        List<Hotel> hotels = new ArrayList<>();
        for (HotelEntity e : entities) hotels.add(toDomain(e));
        return hotels;
    }

    @Override
    public void getAllHotels(HotelListCallback callback) {
        AppExecutors.diskIO(() -> {
            ensureSeeded();
            List<Hotel> hotels = mapAll(db.hotelDao().getAll());
            AppExecutors.mainThread(() -> callback.onResult(hotels));
        });
    }

    @Override
    public void getHotelById(int id, HotelCallback callback) {
        AppExecutors.diskIO(() -> {
            ensureSeeded();
            HotelEntity entity = db.hotelDao().getById(id);
            Hotel hotel = entity == null ? null : toDomain(entity);
            AppExecutors.mainThread(() -> callback.onResult(hotel));
        });
    }

    @Override
    public void search(String city, float minRating, int maxPrice, String roomType, HotelListCallback callback) {
        AppExecutors.diskIO(() -> {
            ensureSeeded();
            List<Hotel> hotels = mapAll(db.hotelDao().search(city, minRating, maxPrice, roomType));
            AppExecutors.mainThread(() -> callback.onResult(hotels));
        });
    }

    @Override
    public void addHotel(Hotel hotel, DoneCallback callback) {
        AppExecutors.diskIO(() -> {
            ensureSeeded();
            HotelEntity entity = toEntity(hotel);
            entity.id = db.hotelDao().maxId() + 1;
            db.hotelDao().insert(entity);
            AppExecutors.mainThread(callback::onComplete);
        });
    }

    @Override
    public void deleteHotel(int id, DoneCallback callback) {
        AppExecutors.diskIO(() -> {
            db.hotelDao().deleteById(id);
            AppExecutors.mainThread(callback::onComplete);
        });
    }

    static HotelEntity toEntity(Hotel hotel) {
        String facilities = String.join(",", hotel.getFacilities());
        return new HotelEntity(hotel.getId(), hotel.getName(), hotel.getCity(), hotel.getAddress(),
                hotel.getRating(), hotel.getPricePerNight(), hotel.getLatitude(), hotel.getLongitude(),
                facilities, RoomTypeCodec.encode(hotel.getRoomTypes()));
    }

    static Hotel toDomain(HotelEntity e) {
        List<String> facilities = e.facilitiesCsv == null || e.facilitiesCsv.isEmpty()
                ? new ArrayList<>() : Arrays.asList(e.facilitiesCsv.split(","));
        return new Hotel(e.id, e.name, e.city, e.address, e.rating, e.pricePerNight,
                e.latitude, e.longitude, facilities, RoomTypeCodec.decode(e.roomTypesRaw));
    }
}
