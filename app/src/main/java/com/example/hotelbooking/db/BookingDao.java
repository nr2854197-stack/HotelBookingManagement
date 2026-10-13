package com.example.hotelbooking.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookingDao {

    @Insert
    long insert(BookingEntity booking);

    @Query("SELECT * FROM bookings WHERE userEmail = :userEmail ORDER BY id DESC")
    List<BookingEntity> getForUser(String userEmail);

    @Query("SELECT * FROM bookings ORDER BY id DESC")
    List<BookingEntity> getAll();

    @Update
    void update(BookingEntity booking);

    @Query("SELECT * FROM bookings WHERE id = :id LIMIT 1")
    BookingEntity getById(int id);
}
