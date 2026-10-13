package com.example.hotelbooking.repository;

import com.example.hotelbooking.models.Review;

import java.util.List;

public interface ReviewRepository {

    interface ReviewListCallback { void onResult(List<Review> reviews); }
    interface DoneCallback { void onComplete(); }

    void getReviews(int hotelId, ReviewListCallback callback);
    void addReview(Review review, DoneCallback callback);
}
