package com.kogo.moapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kogo.moapp.adapters.WatchlistMoviesAdapter;
import com.kogo.moapp.databinding.FragmentWatchlistBinding;
import com.kogo.moapp.db.AppDatabase;
import com.kogo.moapp.db.MoviesForFavorites;
import com.kogo.moapp.db.MoviesDao;
import com.kogo.moapp.db.MoviesForWatchlist;

import java.util.ArrayList;
import java.util.List;


public class WatchlistFragment extends Fragment {

    private List<MoviesForWatchlist> moviesForWatchlists = new ArrayList<>();
    private FragmentWatchlistBinding watchlistBinding;
    private WatchlistMoviesAdapter watchlistMoviesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        watchlistBinding = FragmentWatchlistBinding.inflate(inflater, container, false);

        optionsWatchlistDBandRecyclerview();

        return watchlistBinding.getRoot();
    }

    public void optionsWatchlistDBandRecyclerview(){

        AppDatabase db = Room.databaseBuilder(getActivity(), AppDatabase.class, "database-name").allowMainThreadQueries().build();
        MoviesDao userDao = db.moviesDao();

        moviesForWatchlists.clear();
        moviesForWatchlists = userDao.getAllWatchlistMovies();
        if(moviesForWatchlists.size() == 0){
            watchlistBinding.imageViewWatchListEmpty.setVisibility(View.VISIBLE);
            watchlistBinding.textViewWatchListEmpty.setVisibility(View.VISIBLE);
        }
        else {
            watchlistBinding.imageViewWatchListEmpty.setVisibility(View.INVISIBLE);
            watchlistBinding.textViewWatchListEmpty.setVisibility(View.INVISIBLE);
        }
        watchlistBinding.recyclerViewWatchlist.setHasFixedSize(true);
        watchlistBinding.recyclerViewWatchlist.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        watchlistMoviesAdapter = null;
        watchlistMoviesAdapter = new WatchlistMoviesAdapter(getContext(),moviesForWatchlists);
        watchlistBinding.recyclerViewWatchlist.setAdapter(watchlistMoviesAdapter);
        watchlistMoviesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        optionsWatchlistDBandRecyclerview();    // update recyclerview list

    }
}