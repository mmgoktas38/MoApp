package com.kogo.moapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kogo.moapp.databinding.FragmentFavoritesBinding;
import com.kogo.moapp.databinding.FragmentSearchBinding;

import java.util.ArrayList;
import java.util.List;


public class FavoritesFragment extends Fragment {

    private List<MoviesCopy> moviesCopiesList = new ArrayList<>();
    private FragmentFavoritesBinding favoritesBinding;
    private FavoriteMoviesAdapter favoriteMoviesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        favoritesBinding = FragmentFavoritesBinding.inflate(inflater, container, false);

        AppDatabase db = Room.databaseBuilder(getActivity(),
                AppDatabase.class, "database-name").allowMainThreadQueries().build();

        MoviesDao userDao = db.moviesDao();

        moviesCopiesList = userDao.getAll();


        favoritesBinding.recyclerViewFavorites.setHasFixedSize(true);
        favoritesBinding.recyclerViewFavorites.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        favoriteMoviesAdapter = new FavoriteMoviesAdapter(getContext(),moviesCopiesList);
        favoritesBinding.recyclerViewFavorites.setAdapter(favoriteMoviesAdapter);




        return favoritesBinding.getRoot();
    }
}