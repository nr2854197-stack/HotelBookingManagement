package com.example.hotelbooking.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        entities = {HotelEntity.class, BookingEntity.class, UserEntity.class,
                FavoriteEntity.class, ReviewEntity.class},
        version = 2,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract HotelDao hotelDao();
    public abstract BookingDao bookingDao();
    public abstract UserDao userDao();
    public abstract FavoriteDao favoriteDao();
    public abstract ReviewDao reviewDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(), AppDatabase.class, "localstay.db")
                            // Student-project shortcut: wipe & recreate when the schema changes.
                            // A real release would ship proper Migration objects instead.
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
