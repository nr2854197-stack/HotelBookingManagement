package com.example.hotelbooking.repository;

import com.example.hotelbooking.db.AppDatabase;
import com.example.hotelbooking.db.ReviewEntity;
import com.example.hotelbooking.models.Review;
import com.example.hotelbooking.util.AppExecutors;

import java.util.ArrayList;
import java.util.List;

public class ReviewRepositoryImpl implements ReviewRepository {

    private final AppDatabase db;

    public ReviewRepositoryImpl(AppDatabase db) {
        this.db = db;
    }

    @Override
    public void getReviews(int hotelId, ReviewListCallback callback) {
        AppExecutors.diskIO(() -> {
            List<Review> reviews = new ArrayList<>();
            for (ReviewEntity e : db.reviewDao().getForHotel(hotelId)) {
                reviews.add(new Review(e.id, e.hotelId, e.userName, e.rating, e.comment, e.date));
            }
            AppExecutors.mainThread(() -> callback.onResult(reviews));
        });
    }

    @Override
    public void addReview(Review r, DoneCallback callback) {
        AppExecutors.diskIO(() -> {
            db.reviewDao().insert(new ReviewEntity(0, r.getHotelId(), r.getUserName(), r.getRating(),
                    r.getComment(), r.getDate()));
            AppExecutors.mainThread(callback::onComplete);
        });
    }
}
