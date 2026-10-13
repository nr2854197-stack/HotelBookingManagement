package com.example.hotelbooking.models;

public class RoomType {

    private final String name;
    private final int pricePerNight;
    private final int available;

    public RoomType(String name, int pricePerNight, int available) {
        this.name = name;
        this.pricePerNight = pricePerNight;
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public int getPricePerNight() {
        return pricePerNight;
    }

    public int getAvailable() {
        return available;
    }
}
