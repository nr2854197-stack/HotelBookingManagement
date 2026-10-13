package com.example.hotelbooking.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(FavoriteEntity favorite);

    @Query("DELETE FROM favorites WHERE userEmail = :email AND hotelId = :hotelId")
    void delete(String email, int hotelId);

    @Query("SELECT COUNT(*) FROM favorites WHERE userEmail = :email AND hotelId = :hotelId")
    int isFavorite(String email, int hotelId);

    @Query("SELECT hotelId FROM favorites WHERE userEmail = :email")
    List<Integer> getHotelIds(String email);
}
