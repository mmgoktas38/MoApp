package com.kogo.moapp.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {
        MoviesForFavorites.class,
        MoviesForWatchlist.class
}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MoviesDao moviesDao();
}
