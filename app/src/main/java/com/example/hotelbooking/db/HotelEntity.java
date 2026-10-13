package com.example.hotelbooking.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * facilitiesCsv: "Free WiFi,AC,Breakfast"
 * roomTypesRaw : "Standard:1800:5;Deluxe:2600:3"  (name:price:roomsAvailable)
 */
@Entity(tableName = "hotels")
public class HotelEntity {
    @PrimaryKey
    public int id;
    public String name;
    public String city;
    public String address;
    public float rating;
    public int pricePerNight;
    public double latitude;
    public double longitude;
    public String facilitiesCsv;
    public String roomTypesRaw;

    public HotelEntity(int id, String name, String city, String address, float rating, int pricePerNight,
                       double latitude, double longitude, String facilitiesCsv, String roomTypesRaw) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.address = address;
        this.rating = rating;
        this.pricePerNight = pricePerNight;
        this.latitude = latitude;
        this.longitude = longitude;
        this.facilitiesCsv = facilitiesCsv;
        this.roomTypesRaw = roomTypesRaw;
    }
}
