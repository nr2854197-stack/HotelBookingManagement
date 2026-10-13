package com.example.hotelbooking.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class UserEntity {

    @PrimaryKey
    @NonNull
    public String email;
    public String name;
    public String password;

    public UserEntity(@NonNull String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
