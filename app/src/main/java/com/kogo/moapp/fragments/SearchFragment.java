package com.kogo.moapp.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kogo.moapp.adapters.SearchMovieAdapter;
import com.kogo.moapp.databinding.FragmentSearchBinding;
import com.kogo.moapp.db.MoviesForFavorites;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {

    private FragmentSearchBinding searchBinding;
    public List<MoviesForFavorites> moviesForFavoritesListPopular = new ArrayList<>();
    public List<MoviesForFavorites> moviesForFavoritesListSearches = new ArrayList<>();
    private SearchMovieAdapter searchMovieAdapter;
    private final String urlPopularMovie = "https://api.themoviedb.org/3/movie/popular?api_key=";
    private final String urlSearchMovie = "https://api.themoviedb.org/3/search/movie?api_key=";
    private final String apiKey = "4186844cb1e227ca51b707e60d7238fe";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        searchBinding = FragmentSearchBinding.inflate(inflater, container, false);
        moviesForFavoritesListPopular.clear();
        searchBinding.recyclerViewMovies.setHasFixedSize(true);
        searchBinding.recyclerViewMovies.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        getPopularMovies();

        searchBinding.textViewCancel.setOnClickListener(view -> {
            searchBinding.editTextSearchMovie.getText().clear();
            moviesForFavoritesListPopular.clear();
            moviesForFavoritesListSearches.clear();
            hideKeyboard(getActivity());
        });

        searchBinding.imageViewCancel.setOnClickListener(view -> {
            searchBinding.editTextSearchMovie.getText().clear();
        });

        searchBinding.editTextSearchMovie.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                moviesForFavoritesListSearches.clear();
                searchMovie(editable.toString());
                System.out.println(editable.toString());
            }
        });

        return searchBinding.getRoot();


    }

    public void getPopularMovies(){

        moviesForFavoritesListPopular.clear();
        String popularMoviesURL = urlPopularMovie + apiKey + "&page=1";
        // popularMoviesURL = https://api.themoviedb.org/3/movie/popular?api_key=4186844cb1e227ca51b707e60d7238fe&page=1

        StringRequest stringRequest = new StringRequest(Request.Method.GET, popularMoviesURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String output = response;
                System.out.println(output);
                Log.e("response", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArrayResults = jsonObject.getJSONArray("results");

                    for (int i = 0; i <jsonArrayResults.length(); i++){
                        JSONObject jsonObjectMovie = jsonArrayResults.getJSONObject(i);
                        int id = jsonObjectMovie.getInt("id");
                        String original_title = jsonObjectMovie.getString("original_title");
                        String overview = jsonObjectMovie.getString("overview");
                        String poster_path = jsonObjectMovie.getString("poster_path");
                        String release_date = jsonObjectMovie.getString("release_date");
                        double vote_average = jsonObjectMovie.getDouble("vote_average");


                        MoviesForFavorites m1 = new MoviesForFavorites(id,original_title,overview,poster_path,release_date,vote_average);

                        moviesForFavoritesListPopular.add(m1);
                        searchBinding.recyclerViewMovies.setHasFixedSize(true);
                        searchBinding.recyclerViewMovies.setLayoutManager(new LinearLayoutManager(getContext()));
                        searchMovieAdapter = new SearchMovieAdapter(getContext(), moviesForFavoritesListPopular);
                        searchBinding.recyclerViewMovies.setAdapter(searchMovieAdapter);

                    }


                } catch (JSONException e ) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    public void searchMovie(String text){

        String searchMovieURL = urlSearchMovie + apiKey + "&language=en-US&query=" + text;
        // searchMovieURL = https://api.themoviedb.org/3/search/movie?api_key=4186844cb1e227ca51b707e60d7238fe&query=doctor
        System.out.println(searchMovieURL);

        if (searchBinding.editTextSearchMovie.getText().toString().equals("")){
            moviesForFavoritesListPopular.clear();
            moviesForFavoritesListSearches.clear();
            System.out.println("Burada");
            getPopularMovies();
        }
        else {
            moviesForFavoritesListPopular.clear();
            moviesForFavoritesListSearches.clear();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, searchMovieURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String output = response;
                    System.out.println(output);
                    Log.e("response", response);

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArrayResults = jsonObject.getJSONArray("results");

                        for (int i = 0; i <jsonArrayResults.length(); i++){
                            JSONObject jsonObjectMovie = jsonArrayResults.getJSONObject(i);
                            int id = jsonObjectMovie.getInt(    "id");
                            String original_title = jsonObjectMovie.getString("original_title");
                            String overview = jsonObjectMovie.getString("overview");
                            String poster_path = jsonObjectMovie.getString("poster_path");
                            String release_date = jsonObjectMovie.getString("release_date");
                            double vote_average = jsonObjectMovie.getDouble("vote_average");

                            MoviesForFavorites m1 = new MoviesForFavorites(id,original_title,overview,poster_path,release_date,vote_average);

                            moviesForFavoritesListSearches.add(m1);
                            searchBinding.recyclerViewMovies.setHasFixedSize(true);
                            searchBinding.recyclerViewMovies.setLayoutManager(new LinearLayoutManager(getContext()));
                            searchMovieAdapter = new SearchMovieAdapter(getContext(), moviesForFavoritesListSearches);
                            searchBinding.recyclerViewMovies.setAdapter(searchMovieAdapter);

                        }

                    } catch (JSONException e ) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("error: ", error.toString());
                    error.printStackTrace();
                }
            });

            Volley.newRequestQueue(getContext()).add(stringRequest);
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}