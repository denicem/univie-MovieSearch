package com.denice.a3_moviesearch;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

import java.util.Objects;

public class MainActivity extends AppCompatActivity{

    public static final String MOVIE_DATA_MESSAGE = "movie_data";
    private Button searchButton;
    private Button favButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchButton = findViewById(R.id.search);
        favButton = findViewById(R.id.favourites);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestMovieData();
            }
        });

        //will start "FavouritesActivity" and will list all added favourites
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FavouritesActivity.class);
                startActivity(intent);
            }
        });
    }

    //request to get the movie data from a database
    private void requestMovieData(){
        EditText input = findViewById(R.id.title);
        String title = input.getText().toString();
        title.replaceAll("\\s", "%20");
        String url = "https://www.omdbapi.com/?apikey=f9632181&t=" + title;

        //will request data from the URL link
        RequestQueue rq = Volley.newRequestQueue(this);

        JsonRequest omdbRequest = new JsonObjectRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(final JSONObject response) {
                    Log.i("API_RESPONSE", response.toString());
                    sendRequest(response.toString());
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "Please try again!", Toast.LENGTH_LONG).show();
                    Log.e("API_ERROR", Objects.requireNonNull(error.getMessage()));
                }
            }
        );

        rq.add(omdbRequest);
    }

    //will send the movie data and start the "MovieSearchActivity"
    private void sendRequest(String movieData) {
        Intent intent = new Intent(MainActivity.this, MovieSearchActivity.class);
        intent.putExtra(MOVIE_DATA_MESSAGE, movieData);
        startActivity(intent);
    }
}
