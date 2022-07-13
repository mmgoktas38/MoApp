package com.kogo.moapp;

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

        AppDatabase db = Room.databaseBuilder(getActivity(), AppDatabase.class, "database-name").allowMainThreadQueries().build();
        MoviesDao userDao = db.moviesDao();

        moviesForWatchlists = userDao.getAllWatchlistMovies();

        watchlistBinding.recyclerViewWatchlist.setHasFixedSize(true);
        watchlistBinding.recyclerViewWatchlist.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        watchlistMoviesAdapter = new WatchlistMoviesAdapter(getContext(),moviesForWatchlists);
        watchlistBinding.recyclerViewWatchlist.setAdapter(watchlistMoviesAdapter);

        return watchlistBinding.getRoot();
    }
}