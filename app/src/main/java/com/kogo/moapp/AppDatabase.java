package com.kogo.moapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MoviesCopy.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MoviesDao moviesDao();
}
