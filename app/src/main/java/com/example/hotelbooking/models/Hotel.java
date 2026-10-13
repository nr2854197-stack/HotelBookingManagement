package com.example.hotelbooking.models;

import java.util.List;

public class Hotel {
    private final int id;
    private final String name;
    private final String city;
    private final String address;
    private final float rating;
    private final int pricePerNight;
    private final double latitude;
    private final double longitude;
    private final List<String> facilities;
    private final List<RoomType> roomTypes;

    public Hotel(int id, String name, String city, String address, float rating, int pricePerNight,
                 double latitude, double longitude, List<String> facilities, List<RoomType> roomTypes) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.address = address;
        this.rating = rating;
        this.pricePerNight = pricePerNight;
        this.latitude = latitude;
        this.longitude = longitude;
        this.facilities = facilities;
        this.roomTypes = roomTypes;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCity() { return city; }
    public String getAddress() { return address; }
    public float getRating() { return rating; }
    public int getPricePerNight() { return pricePerNight; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public List<String> getFacilities() { return facilities; }
    public List<RoomType> getRoomTypes() { return roomTypes; }
}
