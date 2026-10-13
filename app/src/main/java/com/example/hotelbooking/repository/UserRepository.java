package com.example.hotelbooking.repository;

public interface UserRepository {

    interface AuthCallback { void onResult(boolean success); }
    interface IntCallback { void onResult(int value); }

    void loginOrRegister(String name, String email, String password, AuthCallback callback);
    void getUserCount(IntCallback callback);
}
