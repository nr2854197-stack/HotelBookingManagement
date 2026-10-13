package com.example.hotelbooking.models;

public class Booking {
    private int id;
    private final int hotelId;
    private final String hotelName;
    private final String roomType;
    private final String checkIn;
    private final String checkOut;
    private final int guests;
    private final int totalPrice;
    private String status;
    private String paymentMethod;
    private String paymentStatus;
    private String userEmail;

    public Booking(int id, int hotelId, String hotelName, String roomType, String checkIn, String checkOut,
                   int guests, int totalPrice, String status, String paymentMethod, String paymentStatus) {
        this.id = id;
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.roomType = roomType;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.guests = guests;
        this.totalPrice = totalPrice;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
    }

    /** New (unsaved) booking. */
    public Booking(int hotelId, String hotelName, String roomType, String checkIn, String checkOut,
                   int guests, int totalPrice, String paymentMethod) {
        this(0, hotelId, hotelName, roomType, checkIn, checkOut, guests, totalPrice,
                "Upcoming", paymentMethod, "Pending");
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getHotelId() { return hotelId; }
    public String getHotelName() { return hotelName; }
    public String getRoomType() { return roomType; }
    public String getCheckIn() { return checkIn; }
    public String getCheckOut() { return checkOut; }
    public int getGuests() { return guests; }
    public int getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
}
