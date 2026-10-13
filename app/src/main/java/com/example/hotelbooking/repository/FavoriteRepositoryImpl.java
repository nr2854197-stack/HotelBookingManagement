package com.example.hotelbooking.repository;

import com.example.hotelbooking.db.AppDatabase;
import com.example.hotelbooking.db.FavoriteEntity;
import com.example.hotelbooking.db.HotelEntity;
import com.example.hotelbooking.models.Hotel;
import com.example.hotelbooking.util.AppExecutors;

import java.util.ArrayList;
import java.util.List;

public class FavoriteRepositoryImpl implements FavoriteRepository {

    private final AppDatabase db;

    public FavoriteRepositoryImpl(AppDatabase db) {
        this.db = db;
    }

    @Override
    public void isFavorite(String email, int hotelId, BoolCallback callback) {
        AppExecutors.diskIO(() -> {
            boolean fav = db.favoriteDao().isFavorite(email, hotelId) > 0;
            AppExecutors.mainThread(() -> callback.onResult(fav));
        });
    }

    @Override
    public void setFavorite(String email, int hotelId, boolean favorite, BoolCallback callback) {
        AppExecutors.diskIO(() -> {
            if (favorite) db.favoriteDao().insert(new FavoriteEntity(email, hotelId));
            else db.favoriteDao().delete(email, hotelId);
            AppExecutors.mainThread(() -> callback.onResult(favorite));
        });
    }

    @Override
    public void getFavoriteHotels(String email, HotelRepository.HotelListCallback callback) {
        AppExecutors.diskIO(() -> {
            List<Integer> ids = db.favoriteDao().getHotelIds(email);
            List<Hotel> hotels = new ArrayList<>();
            if (!ids.isEmpty()) {
                for (HotelEntity e : db.hotelDao().getByIds(ids)) hotels.add(HotelRepositoryImpl.toDomain(e));
            }
            AppExecutors.mainThread(() -> callback.onResult(hotels));
        });
    }
}
