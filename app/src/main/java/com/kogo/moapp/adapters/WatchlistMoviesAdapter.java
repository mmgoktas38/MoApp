package com.kogo.moapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.kogo.moapp.MovieDetailsActivity;
import com.kogo.moapp.R;
import com.kogo.moapp.db.MoviesForFavorites;
import com.kogo.moapp.db.MoviesForWatchlist;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WatchlistMoviesAdapter extends RecyclerView.Adapter<WatchlistMoviesAdapter.CardViewHolder>{

    private Context mContext;
    private List<MoviesForWatchlist> moviesList;
    RecyclerView mRecyclerView;


    public WatchlistMoviesAdapter(Context mContext, List<MoviesForWatchlist> moviesList) {
        this.mContext = mContext;
        this.moviesList = moviesList;
    }

    @NonNull
    @Override
    public WatchlistMoviesAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_design_favorites_watchlist, parent, false);
        mRecyclerView = (RecyclerView) parent;
        return new WatchlistMoviesAdapter.CardViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull WatchlistMoviesAdapter.CardViewHolder holder, int position) {

        MoviesForWatchlist movie = moviesList.get(position);

        String[] arrOfStr = movie.getRelease_date().split("-");


        holder.textViewMovieNameYear.setText(movie.getOriginal_title() + " - " + arrOfStr[0]);
        holder.cLayoutMovieSearch.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, MovieDetailsActivity.class);
            intent.putExtra("fromWatchlist",1);
            intent.putExtra("Movie Id", movie.getFilm_id());
            intent.putExtra("Movie Title", movie.getOriginal_title());
            intent.putExtra("Movie Overview", movie.getOverview());
            intent.putExtra("Movie Poster", movie.getPoster_path());
            intent.putExtra("Movie Release Date", movie.getRelease_date());
            intent.putExtra("Movie Vote Average", movie.getVote_average());
            mContext.startActivity(intent);
        });
        String photo_url_str = "https://image.tmdb.org/t/p/original/" + movie.getPoster_path();
        Picasso.with(mContext).load(photo_url_str).into(holder.imageViewPoster);

    }



    @Override
    public int getItemCount() {
        return moviesList.size();
    }


    public class CardViewHolder extends RecyclerView.ViewHolder {

        public ConstraintLayout cLayoutMovieSearch;
        public TextView textViewMovieNameYear;
        public ImageView imageViewPoster;


        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            cLayoutMovieSearch = itemView.findViewById(R.id.cLayoutMovieSearch);
            textViewMovieNameYear = itemView.findViewById(R.id.textViewMovieNameYear);
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster);


        }
    }


}
