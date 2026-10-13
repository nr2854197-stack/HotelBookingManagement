package com.example.hotelbooking.repository;

import com.example.hotelbooking.db.AppDatabase;
import com.example.hotelbooking.db.UserEntity;
import com.example.hotelbooking.util.AppExecutors;

public class UserRepositoryImpl implements UserRepository {

    private final AppDatabase db;

    public UserRepositoryImpl(AppDatabase db) {
        this.db = db;
    }

    @Override
    public void loginOrRegister(String name, String email, String password, AuthCallback callback) {
        AppExecutors.diskIO(() -> {
            UserEntity existing = db.userDao().getByEmail(email);
            boolean success;
            if (existing != null) {
                // Demo-only auth: plain-text password compare. Use hashing / Firebase Auth for real users.
                success = existing.password.equals(password);
            } else {
                db.userDao().upsert(new UserEntity(email, name, password));
                success = true;
            }
            boolean result = success;
            AppExecutors.mainThread(() -> callback.onResult(result));
        });
    }

    @Override
    public void getUserCount(IntCallback callback) {
        AppExecutors.diskIO(() -> {
            int count = db.userDao().count();
            AppExecutors.mainThread(() -> callback.onResult(count));
        });
    }
}
