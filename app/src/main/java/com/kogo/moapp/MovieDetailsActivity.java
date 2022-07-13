package com.kogo.moapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;

import com.kogo.moapp.databinding.ActivityMovieDetailsBinding;
import com.kogo.moapp.db.AppDatabase;
import com.kogo.moapp.db.MoviesForFavorites;
import com.kogo.moapp.db.MoviesDao;
import com.kogo.moapp.db.MoviesForWatchlist;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    private ActivityMovieDetailsBinding movieDetailsBinding;

    private AppDatabase db;
    private MoviesDao userDao;
    private int movieId;
    private String movieTitle, movieOverview, moviePoster, movieReleaseDate;
    private double movieVoteAverage;
    private MoviesForFavorites getFavoriteMovie;
    private MoviesForWatchlist getWatchlistMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieDetailsBinding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(movieDetailsBinding.getRoot());

        db = Room.databaseBuilder(MovieDetailsActivity.this, AppDatabase.class, "database-name").allowMainThreadQueries().build();
        userDao = db.moviesDao();

        // get datas with getIntent
        movieId = getIntent().getIntExtra("Movie Id",11111);
        movieTitle = getIntent().getStringExtra("Movie Title");
        movieOverview = getIntent().getStringExtra("Movie Overview");
        moviePoster = getIntent().getStringExtra("Movie Poster");
        movieReleaseDate = getIntent().getStringExtra("Movie Release Date");
        movieVoteAverage = getIntent().getDoubleExtra("Movie Vote Average",0.0);

        // get movie from database by movieId
        getFavoriteMovie = userDao.getFavoritesMovieById(movieId);
        getWatchlistMovie = userDao.getWatchlistMovieById(movieId);
        // control, if movie in our favoriteslist or watchlist
        MoviesForFavorites moviesForFavorites = isMovieInFavorites();
        MoviesForWatchlist moviesForWatchlist = isMovieInWatchlist();

        String photo_url_str = "https://image.tmdb.org/t/p/original/" + moviePoster;
        Picasso.with(getApplicationContext()).load(photo_url_str).into(movieDetailsBinding.imageViewMoviePoster);
        movieDetailsBinding.textViewVoteAverage.setText(String.valueOf(movieVoteAverage));
        movieDetailsBinding.textViewDate.setText(movieReleaseDate);
        movieDetailsBinding.textViewMovieTitle.setText(movieTitle);
        movieDetailsBinding.textViewMovieOverview.setText(movieOverview);

        movieDetailsBinding.textViewBackToSearch.setOnClickListener(view -> {
            Intent intent = new Intent(MovieDetailsActivity.this, MenuActivity.class);
            startActivity(intent);
        });
        movieDetailsBinding.imageViewBackToSearch.setOnClickListener(view -> {
            Intent intent = new Intent(MovieDetailsActivity.this, MenuActivity.class);
            startActivity(intent);
        });

        movieDetailsBinding.imageViewSaveToFavorites.setOnClickListener(view -> {

            if (getFavoriteMovie == null){
                userDao.insertAll(moviesForFavorites);
                movieDetailsBinding.imageViewSaveToFavorites.setImageResource(R.drawable.favorite_film);
            }
            else{
                userDao.delete(getFavoriteMovie);
                movieDetailsBinding.imageViewSaveToFavorites.setImageResource(R.drawable.favorites);
            }

        });
        movieDetailsBinding.imageViewSaveToWatchlist.setOnClickListener(view -> {

            if (getWatchlistMovie == null){
                userDao.insertAllMoviesForWatchlist(moviesForWatchlist);
                movieDetailsBinding.imageViewSaveToWatchlist.setImageResource(R.drawable.watchlist_film);
            }
            else{
                userDao.deleteMoviesForWatchlist(getWatchlistMovie);
                movieDetailsBinding.imageViewSaveToWatchlist.setImageResource(R.drawable.watchlist);
            }

        });


    }

    public MoviesForFavorites isMovieInFavorites(){

        if (getFavoriteMovie == null){
            movieDetailsBinding.imageViewSaveToFavorites.setImageResource(R.drawable.favorites);
        }
        else{
            movieDetailsBinding.imageViewSaveToFavorites.setImageResource(R.drawable.favorite_film);
        }

        MoviesForFavorites moviesForFavorites = new MoviesForFavorites(movieId,movieTitle,movieOverview,moviePoster,movieReleaseDate,movieVoteAverage);

        return moviesForFavorites;

    }

    public MoviesForWatchlist isMovieInWatchlist(){

        if (getWatchlistMovie == null){
            movieDetailsBinding.imageViewSaveToWatchlist.setImageResource(R.drawable.watchlist);
        }
        else{
            movieDetailsBinding.imageViewSaveToWatchlist.setImageResource(R.drawable.watchlist_film);
        }

        MoviesForWatchlist moviesForWatchlist = new MoviesForWatchlist(movieId,movieTitle,movieOverview,moviePoster,movieReleaseDate,movieVoteAverage);

        return moviesForWatchlist;

    }

}