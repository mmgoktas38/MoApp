package com.kogo.moapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;

import com.kogo.moapp.R;
import com.kogo.moapp.adapters.FavoriteMoviesAdapter;
import com.kogo.moapp.databinding.ActivityMovieDetailsBinding;
import com.kogo.moapp.db.AppDatabase;
import com.kogo.moapp.db.MoviesForFavorites;
import com.kogo.moapp.db.MoviesDao;
import com.kogo.moapp.db.MoviesForWatchlist;
import com.kogo.moapp.fragments.FavoritesFragment;
import com.kogo.moapp.fragments.WatchlistFragment;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    private ActivityMovieDetailsBinding movieDetailsBinding;

    private AppDatabase db;
    private MoviesDao userDao;
    private int movieId, fromWhatchlist, fromFavorites;
    private String movieTitle, movieOverview, moviePoster, movieReleaseDate;
    private double movieVoteAverage;
    private MoviesForFavorites getFavoriteMovie, moviesForFavorites;
    private MoviesForWatchlist getWatchlistMovie, moviesForWatchlist;
    private FavoriteMoviesAdapter favoriteMoviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieDetailsBinding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(movieDetailsBinding.getRoot());

        db = Room.databaseBuilder(MovieDetailsActivity.this, AppDatabase.class, "database-name").allowMainThreadQueries().build();
        userDao = db.moviesDao();

        // get datas with getIntent
        fromFavorites = getIntent().getIntExtra("fromFavorites", 0);
        fromWhatchlist = getIntent().getIntExtra("fromWatchlist", 0);
        movieId = getIntent().getIntExtra("Movie Id",11111);
        movieTitle = getIntent().getStringExtra("Movie Title");
        movieOverview = getIntent().getStringExtra("Movie Overview");
        moviePoster = getIntent().getStringExtra("Movie Poster");
        movieReleaseDate = getIntent().getStringExtra("Movie Release Date");
        movieVoteAverage = getIntent().getDoubleExtra("Movie Vote Average",0.0);

        if (fromFavorites == 1 && fromWhatchlist == 0){
            movieDetailsBinding.textViewBackToSearch.setText("Favorites");
        }
        if (fromFavorites == 0 && fromWhatchlist == 1){
            movieDetailsBinding.textViewBackToSearch.setText("Watchlist");
        }
        if (fromFavorites == 0 && fromWhatchlist == 0){
            movieDetailsBinding.textViewBackToSearch.setText("Search");
        }

        movieDetailsBinding.textViewBackToSearch.setOnClickListener(view -> {
           // onBackPressed();
            goBackPage();
        });
        movieDetailsBinding.imageViewBackToSearch.setOnClickListener(view -> {
           // onBackPressed();
            goBackPage();
        });


        // get movie from database by movieId
        getFavoriteMovie = userDao.getFavoritesMovieById(movieId);
        getWatchlistMovie = userDao.getWatchlistMovieById(movieId);

        // control, if movie in our favoriteslist database or watchlist database
        moviesForFavorites = isMovieInFavorites();
        moviesForWatchlist = isMovieInWatchlist();

        String photo_url_str = "https://image.tmdb.org/t/p/original/" + moviePoster;
        Picasso.with(getApplicationContext()).load(photo_url_str).into(movieDetailsBinding.imageViewMoviePoster);
        movieDetailsBinding.textViewVoteAverage.setText(String.valueOf(movieVoteAverage));
        movieDetailsBinding.textViewDate.setText(movieReleaseDate);
        movieDetailsBinding.textViewMovieTitle.setText(movieTitle);
        movieDetailsBinding.textViewMovieOverview.setText(movieOverview);


        movieDetailsBinding.imageViewSaveToFavorites.setOnClickListener(view -> {

            // get movie from database by movieId
            getFavoriteMovie = userDao.getFavoritesMovieById(movieId);

            if (getFavoriteMovie == null){
                System.out.println("eklendi" +  moviesForFavorites.getFilm_id());
                userDao.insertAll(moviesForFavorites);
                movieDetailsBinding.imageViewSaveToFavorites.setImageResource(R.drawable.favorite_film);
            }
            else{
                System.out.println("çıkarıldı" + getFavoriteMovie.getFilm_id());
                userDao.delete(getFavoriteMovie);
                movieDetailsBinding.imageViewSaveToFavorites.setImageResource(R.drawable.favorites);
            }

        });
        movieDetailsBinding.imageViewSaveToWatchlist.setOnClickListener(view -> {

            // get movie from database by movieId
            getWatchlistMovie = userDao.getWatchlistMovieById(movieId);

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

    public void goBackPage(){
        if (fromFavorites == 1 && fromWhatchlist == 0){
            Fragment favoritesFragment = new FavoritesFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.cLmovieDetailActivity, favoritesFragment).attach(favoritesFragment).commit();
            finish();
        }
        if (fromFavorites == 0 && fromWhatchlist == 1){
            Fragment watchlistFragment = new WatchlistFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.cLmovieDetailActivity, watchlistFragment).attach(watchlistFragment).commit();
            finish();
        }
        if (fromFavorites == 0 && fromWhatchlist == 0){
            Intent intent = new Intent(MovieDetailsActivity.this, MenuActivity.class);
            startActivity(intent);
        }
    }


    public MoviesForFavorites isMovieInFavorites(){

        if (getFavoriteMovie == null){
            movieDetailsBinding.imageViewSaveToFavorites.setImageResource(R.drawable.favorites);
            MoviesForFavorites moviesForFavorites = new MoviesForFavorites(movieId,movieTitle,movieOverview,moviePoster,movieReleaseDate,movieVoteAverage);
            return moviesForFavorites;
        }
        else{
            movieDetailsBinding.imageViewSaveToFavorites.setImageResource(R.drawable.favorite_film);
            return getFavoriteMovie;
        }

    }

    public MoviesForWatchlist isMovieInWatchlist(){

        if (getWatchlistMovie == null){
            movieDetailsBinding.imageViewSaveToWatchlist.setImageResource(R.drawable.watchlist);
            MoviesForWatchlist moviesForWatchlist = new MoviesForWatchlist(movieId,movieTitle,movieOverview,moviePoster,movieReleaseDate,movieVoteAverage);
            return moviesForWatchlist;
        }
        else{
            movieDetailsBinding.imageViewSaveToWatchlist.setImageResource(R.drawable.watchlist_film);
            return getWatchlistMovie;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}