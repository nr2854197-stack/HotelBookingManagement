package com.example.hotelbooking.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "favorites", primaryKeys = {"userEmail", "hotelId"})
public class FavoriteEntity {
    @NonNull
    public String userEmail;
    public int hotelId;

    public FavoriteEntity(@NonNull String userEmail, int hotelId) {
        this.userEmail = userEmail;
        this.hotelId = hotelId;
    }
}
