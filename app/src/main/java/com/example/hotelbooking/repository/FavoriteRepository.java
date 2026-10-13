package com.example.hotelbooking.repository;

public interface FavoriteRepository {

    interface BoolCallback { void onResult(boolean value); }

    void isFavorite(String email, int hotelId, BoolCallback callback);

    /** Saves or removes the favorite; callback receives the new state. */
    void setFavorite(String email, int hotelId, boolean favorite, BoolCallback callback);

    void getFavoriteHotels(String email, HotelRepository.HotelListCallback callback);
}
