package com.example.hotelbooking.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ReviewDao {

    @Insert
    void insert(ReviewEntity review);

    @Query("SELECT * FROM reviews WHERE hotelId = :hotelId ORDER BY id DESC")
    List<ReviewEntity> getForHotel(int hotelId);
}
