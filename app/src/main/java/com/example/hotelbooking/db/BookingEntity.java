package com.example.hotelbooking.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookings")
public class BookingEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int hotelId;
    public String hotelName;
    public String roomType;
    public String checkIn;
    public String checkOut;
    public int guests;
    public int totalPrice;
    public String status;
    public String userEmail;
    public String paymentMethod;
    public String paymentStatus;

    public BookingEntity(int id, int hotelId, String hotelName, String roomType, String checkIn,
                         String checkOut, int guests, int totalPrice, String status, String userEmail,
                         String paymentMethod, String paymentStatus) {
        this.id = id;
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.roomType = roomType;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.guests = guests;
        this.totalPrice = totalPrice;
        this.status = status;
        this.userEmail = userEmail;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
    }
}
