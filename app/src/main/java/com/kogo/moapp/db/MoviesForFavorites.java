package com.kogo.moapp.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites_movies")
public class MoviesForFavorites {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "index_id")
    private int index_id;
    @ColumnInfo(name = "film_id")
    private int film_id;
    @ColumnInfo(name = "original_title")
    private String original_title;
    @ColumnInfo(name = "overview")
    private String overview;
    @ColumnInfo(name = "poster_path")
    private String poster_path;
    @ColumnInfo(name = "release_date")
    private String release_date;
    @ColumnInfo(name = "vote_average")
    private double vote_average;

    public MoviesForFavorites() {
    }


    public MoviesForFavorites(int film_id, String original_title, String overview, String poster_path, String release_date, double vote_average) {
        this.film_id = film_id;
        this.original_title = original_title;
        this.overview = overview;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.vote_average = vote_average;
    }

    public int getIndex_id() {
        return index_id;
    }

    public void setIndex_id(int index_id) {
        this.index_id = index_id;
    }



    public int getFilm_id() {
        return film_id;
    }

    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {

        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(int vote_average) {
        this.vote_average = vote_average;
    }

}
