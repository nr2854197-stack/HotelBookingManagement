package com.example.hotelbooking.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HotelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<HotelEntity> hotels);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(HotelEntity hotel);

    @Update
    void update(HotelEntity hotel);

    @Query("DELETE FROM hotels WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT IFNULL(MAX(id), 0) FROM hotels")
    int maxId();

    @Query("SELECT COUNT(*) FROM hotels")
    int count();

    @Query("SELECT * FROM hotels ORDER BY rating DESC")
    List<HotelEntity> getAll();

    @Query("SELECT * FROM hotels WHERE id = :id LIMIT 1")
    HotelEntity getById(int id);

    @Query("SELECT * FROM hotels WHERE id IN (:ids) ORDER BY rating DESC")
    List<HotelEntity> getByIds(List<Integer> ids);

    @Query("SELECT * FROM hotels " +
            "WHERE (:city = '' OR LOWER(city) LIKE '%' || LOWER(:city) || '%' OR LOWER(address) LIKE '%' || LOWER(:city) || '%') " +
            "AND rating >= :minRating " +
            "AND pricePerNight <= :maxPrice " +
            "AND (:roomType = '' OR roomTypesRaw LIKE '%' || :roomType || ':%') " +
            "ORDER BY rating DESC")
    List<HotelEntity> search(String city, float minRating, int maxPrice, String roomType);
}
