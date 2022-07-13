package com.kogo.moapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MoviesDao {

    @Query("SELECT * FROM favorites_movies")
    List<MoviesForFavorites> getAll();

    @Insert
    void insertAll(MoviesForFavorites... moviesCopies);

    @Delete
    void delete(MoviesForFavorites moviesForFavorites);

    @Query("SELECT * FROM favorites_movies WHERE film_id == :filmId")
    MoviesForFavorites getFavoritesMovieById(int filmId);



    @Query("SELECT * FROM watchlist_movies WHERE film_id == :filmId")
    MoviesForWatchlist getWatchlistMovieById(int filmId);

    @Query("SELECT * FROM watchlist_movies")
    List<MoviesForWatchlist> getAllWatchlistMovies();

    @Insert
    void insertAllMoviesForWatchlist(MoviesForWatchlist... moviesForWatchlists);

    @Delete
    void deleteMoviesForWatchlist(MoviesForWatchlist moviesForWatchlist);


}
