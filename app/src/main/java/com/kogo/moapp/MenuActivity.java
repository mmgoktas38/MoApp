package com.kogo.moapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kogo.moapp.adapters.FavoriteMoviesAdapter;
import com.kogo.moapp.db.AppDatabase;
import com.kogo.moapp.db.MoviesDao;
import com.kogo.moapp.db.MoviesForFavorites;
import com.kogo.moapp.fragments.FavoritesFragment;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        bottomNavigation = findViewById(R.id.bottomNavigation);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHostFragment);

        NavigationUI.setupWithNavController(bottomNavigation, navHostFragment.getNavController());
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println(" menu activity onStart");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.out.println("menu activity onbackpressed");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println(" menu activity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println(" menu activity onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println(" menu activity onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println(" menu activity onDestroy");
    }
}