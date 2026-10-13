package com.example.hotelbooking.repository;

import com.example.hotelbooking.models.Hotel;

import java.util.List;

/**
 * Screens talk only to repository interfaces. Today they're backed by Room; a Firebase/REST
 * implementation can replace them in RepositoryProvider without touching any screen.
 * Results are always delivered on the main thread.
 */
public interface HotelRepository {

    interface HotelListCallback { void onResult(List<Hotel> hotels); }
    interface HotelCallback { void onResult(Hotel hotel); }
    interface DoneCallback { void onComplete(); }

    void getAllHotels(HotelListCallback callback);
    void getHotelById(int id, HotelCallback callback);
    void search(String city, float minRating, int maxPrice, String roomType, HotelListCallback callback);
    void addHotel(Hotel hotel, DoneCallback callback);
    void deleteHotel(int id, DoneCallback callback);
}
