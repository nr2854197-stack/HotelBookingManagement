package com.example.hotelbooking.models;

public class Review {
    private final int id;
    private final int hotelId;
    private final String userName;
    private final float rating;
    private final String comment;
    private final String date;

    public Review(int id, int hotelId, String userName, float rating, String comment, String date) {
        this.id = id;
        this.hotelId = hotelId;
        this.userName = userName;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public int getId() { return id; }
    public int getHotelId() { return hotelId; }
    public String getUserName() { return userName; }
    public float getRating() { return rating; }
    public String getComment() { return comment; }
    public String getDate() { return date; }
}
