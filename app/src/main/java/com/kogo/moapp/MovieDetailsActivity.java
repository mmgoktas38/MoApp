package com.kogo.moapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.kogo.moapp.databinding.ActivityMovieDetailsBinding;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MovieDetailsActivity extends AppCompatActivity {

    private ActivityMovieDetailsBinding movieDetailsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieDetailsBinding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(movieDetailsBinding.getRoot());

        AppDatabase db = Room.databaseBuilder(MovieDetailsActivity.this,
                AppDatabase.class, "database-name").allowMainThreadQueries().build();

        String movieTitle = getIntent().getStringExtra("Movie Title");
        String movieOverview = getIntent().getStringExtra("Movie Overview");
        String moviePoster = getIntent().getStringExtra("Movie Poster");
        String movieReleaseDate = getIntent().getStringExtra("Movie Release Date");
        double movieVoteAverage = getIntent().getDoubleExtra("Movie Vote Average",0.0);
        System.out.println(moviePoster);

        MoviesCopy moviesCopy = new MoviesCopy(2,movieTitle,movieOverview,moviePoster,movieReleaseDate,movieVoteAverage);

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
            MoviesDao userDao = db.moviesDao();
            userDao.insertAll(moviesCopy);
        });


    }
}