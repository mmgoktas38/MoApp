package com.kogo.moapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MoviesDao {

    @Query("SELECT * FROM movies_table")
    List<MoviesCopy> getAll();

    @Insert
    void insertAll(MoviesCopy ... moviesCopies);

    @Delete
    void delete(MoviesCopy moviesCopy);

}
