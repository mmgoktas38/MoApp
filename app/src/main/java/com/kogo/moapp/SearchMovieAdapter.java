package com.kogo.moapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchMovieAdapter extends RecyclerView.Adapter<SearchMovieAdapter.CardViewHolder> {

    private Context mContext;
    private List<Movies> moviesList;


    public SearchMovieAdapter(Context mContext, List<Movies> moviesList) {
        this.mContext = mContext;
        this.moviesList = moviesList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_design_search, parent, false);

        return new CardViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Movies movie = moviesList.get(position);

        String[] arrOfStr = movie.getRelease_date().split("-");

        holder.textViewMovieNameYear.setText(movie.getOriginal_title() + " - " + arrOfStr[0]);
        holder.cLayoutMovieSearch.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, MovieDetailsActivity.class);
            intent.putExtra("Movie Title", movie.getOriginal_title());
            intent.putExtra("Movie Overview", movie.getOverview());
            intent.putExtra("Movie Poster", movie.getPoster_path());
            intent.putExtra("Movie Release Date", movie.getRelease_date());
            intent.putExtra("Movie Vote Average", movie.getVote_average());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }


    public class CardViewHolder extends RecyclerView.ViewHolder {

        public ConstraintLayout cLayoutMovieSearch;
        public TextView textViewMovieNameYear;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            cLayoutMovieSearch = itemView.findViewById(R.id.cLayoutMovieSearch);
            textViewMovieNameYear = itemView.findViewById(R.id.textViewMovieNameYear);

        }
    }
}
