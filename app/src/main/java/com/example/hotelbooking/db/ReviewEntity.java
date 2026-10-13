package com.example.hotelbooking.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reviews")
public class ReviewEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int hotelId;
    public String userName;
    public float rating;
    public String comment;
    public String date;

    public ReviewEntity(int id, int hotelId, String userName, float rating, String comment, String date) {
        this.id = id;
        this.hotelId = hotelId;
        this.userName = userName;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }
}
