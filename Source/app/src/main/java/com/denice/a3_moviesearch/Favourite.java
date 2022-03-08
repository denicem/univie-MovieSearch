package com.denice.a3_moviesearch;

import org.json.JSONObject;
import org.json.JSONException;

public class Favourite {
    private String jsonFile;
    private String poster;
    private String title;
    private String year;
    private String genre;

    Favourite(String jsonFile) throws JSONException {
        this.jsonFile = jsonFile;

        JSONObject jsonObject = new JSONObject(jsonFile);
        poster = jsonObject.getString("Poster");
        title = jsonObject.getString("Title");
        year = jsonObject.getString("Year");
        genre = jsonObject.getString("Genre");
    }

    public String getJsonFile() {
        return jsonFile;
    }
    public String getPoster() {
        return poster;
    }
    public String getTitle() {
        return title;
    }
    public String getYear() {
        return year;
    }
    public String getGenre() {
        return genre;
    }
}
