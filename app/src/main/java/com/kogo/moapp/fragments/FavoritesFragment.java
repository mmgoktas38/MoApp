package com.kogo.moapp.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kogo.moapp.R;
import com.kogo.moapp.adapters.FavoriteMoviesAdapter;
import com.kogo.moapp.databinding.FragmentFavoritesBinding;
import com.kogo.moapp.db.AppDatabase;
import com.kogo.moapp.db.MoviesForFavorites;
import com.kogo.moapp.db.MoviesDao;

import java.lang.ref.PhantomReference;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;


public class FavoritesFragment extends Fragment {

    private List<MoviesForFavorites> moviesForFavoritesList = new ArrayList<>();
    private FragmentFavoritesBinding favoritesBinding;
    private FavoriteMoviesAdapter favoriteMoviesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        favoritesBinding = FragmentFavoritesBinding.inflate(inflater, container, false);

        optionsFavoritesDBandRecyclerview();

        return favoritesBinding.getRoot();
    }

    public void optionsFavoritesDBandRecyclerview(){

        AppDatabase db = Room.databaseBuilder(getActivity(), AppDatabase.class, "database-name").allowMainThreadQueries().build();
        MoviesDao userDao = db.moviesDao();

        moviesForFavoritesList.clear();
        moviesForFavoritesList = userDao.getAll();
        if(moviesForFavoritesList.size() == 0){
            favoritesBinding.imageViewFavoritesListEmpty.setVisibility(View.VISIBLE);
            favoritesBinding.textViewFavoritesListEmpty.setVisibility(View.VISIBLE);
        }
        else {
            favoritesBinding.imageViewFavoritesListEmpty.setVisibility(View.INVISIBLE);
            favoritesBinding.textViewFavoritesListEmpty.setVisibility(View.INVISIBLE);
        }
        favoritesBinding.recyclerViewFavorites.setHasFixedSize(true);
        favoritesBinding.recyclerViewFavorites.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        favoriteMoviesAdapter = null;
        favoriteMoviesAdapter = new FavoriteMoviesAdapter(getContext(),moviesForFavoritesList);
        favoritesBinding.recyclerViewFavorites.setAdapter(favoriteMoviesAdapter);
        favoriteMoviesAdapter.notifyDataSetChanged();

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        optionsFavoritesDBandRecyclerview();  // update recyclerview list

    }
}