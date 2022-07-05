package com.kogo.moapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class WatchlistFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewWatchlist = inflater.inflate(R.layout.fragment_watchlist, container, false);

        return viewWatchlist;
    }
}