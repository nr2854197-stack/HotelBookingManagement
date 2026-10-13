package com.example.hotelbooking.repository;

import android.content.Context;

import com.example.hotelbooking.db.AppDatabase;

/** Single place that decides which implementation backs each repository (swap here for a real backend). */
public final class RepositoryProvider {

    private RepositoryProvider() { }

    public static HotelRepository hotelRepository(Context c) {
        return new HotelRepositoryImpl(AppDatabase.getInstance(c));
    }

    public static BookingRepository bookingRepository(Context c) {
        return new BookingRepositoryImpl(AppDatabase.getInstance(c));
    }

    public static UserRepository userRepository(Context c) {
        return new UserRepositoryImpl(AppDatabase.getInstance(c));
    }

    public static FavoriteRepository favoriteRepository(Context c) {
        return new FavoriteRepositoryImpl(AppDatabase.getInstance(c));
    }

    public static ReviewRepository reviewRepository(Context c) {
        return new ReviewRepositoryImpl(AppDatabase.getInstance(c));
    }
}
