package com.denice.a3_moviesearch;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieSearchActivity extends AppCompatActivity {

    private String movieData;
    private ImageButton addFavButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);

        Intent intent = getIntent();
        movieData = intent.getStringExtra(MainActivity.MOVIE_DATA_MESSAGE);
        addFavButton = findViewById(R.id.addFavButton);

        if (!listData(movieData)) { //if no movie was found, all pre-loaded elements will be removed and set a note "N/A"
            TextView notAvailableText = findViewById(R.id.notAvailableText);
            View divider = findViewById(R.id.divider);

            divider.setVisibility(View.GONE);
            addFavButton.setVisibility(View.GONE);

            notAvailableText.setText("N/A");
        }

        //will add the current movie to favourites
        addFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject obj = new JSONObject(movieData);
                    for (int i = 0; i < FavouritesActivity.favList.size(); ++i) {
                        if (FavouritesActivity.favList.get(i).getTitle().equals(obj.getString("Title"))) {
                            FavouritesActivity.favList.remove(i);
                            addFavButton.setImageResource(R.drawable.ic_star_outline_white_36dp);
                            Toast.makeText(MovieSearchActivity.this, "Removed from favourites.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    FavouritesActivity.addFav(new Favourite(movieData));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                addFavButton.setImageResource(R.drawable.ic_star_white_36dp);
                Toast.makeText(MovieSearchActivity.this, "Added to favourites.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @SuppressLint("SetTextI18n")
    boolean listData(String movieData) {
        boolean res = false;
        try {
            JSONObject obj = new JSONObject(movieData);
            if (obj.getString("Response").equals("True")) {
                res = true;

                for (int i = 0; i < FavouritesActivity.favList.size(); ++i) {
                    if (FavouritesActivity.favList.get(i).getTitle().equals(obj.getString("Title"))) {
                        addFavButton.setImageResource(R.drawable.ic_star_white_36dp);
                    }
                }

                //will take elements from Layout
                TextView movie = findViewById(R.id.movie);
                ImageView poster = findViewById(R.id.poster);
                TextView year = findViewById(R.id.year);
                TextView genre = findViewById(R.id.genre);
                TextView directorTitle = findViewById(R.id.directorTitle);
                TextView director = findViewById(R.id.director);
                TextView writerTitle = findViewById(R.id.writerTitle);
                TextView writer = findViewById(R.id.writer);
                TextView actorsTitle = findViewById(R.id.actorsTitle);
                TextView actor = findViewById(R.id.actors);
                ImageView imdbIcon = findViewById(R.id.imdbIcon);
                TextView imdb = findViewById(R.id.imdb);
                ImageView rtIcon = findViewById(R.id.rtIcon);
                TextView rottenTomato = findViewById(R.id.rottenTomato);
                ImageView metaIcon = findViewById(R.id.metaIcon);
                TextView metacritic = findViewById(R.id.metacritic);

                //set the movie data to each elements
                movie.setText(obj.getString("Title"));
                Picasso.get().load(obj.getString("Poster")).into(poster);
                year.setText(obj.getString("Year"));
                genre.setText((obj.getString("Genre")));
                directorTitle.setText("Director(s):");
                director.setText(obj.getString("Director"));
                writerTitle.setText("Writer(s):");
                writer.setText(obj.getString("Writer"));
                actorsTitle.setText("Cast:");
                actor.setText(obj.getString("Actors"));

                imdbIcon.setImageResource(R.drawable.imdb);
                rtIcon.setImageResource(R.drawable.rotten_tomato);
                metaIcon.setImageResource(R.drawable.metacritic);

                JSONArray ratings = obj.getJSONArray("Ratings");
                imdb.setText("N/A");
                Log.i("IMDB_TEXT", imdb.getText().toString());
                rottenTomato.setText("N/A");
                metacritic.setText("N/A");
                for (int i = 0; i < ratings.length(); ++i) {
                    obj = ratings.getJSONObject(i);
                    switch (obj.getString("Source")) {
                        case "Internet Movie Database":
                            imdb.setText(obj.getString("Value"));
                            break;
                        case "Rotten Tomatoes":
                            rottenTomato.setText(obj.getString("Value"));
                            break;
                        case "Metacritic":
                            metacritic.setText(obj.getString("Value"));
                            break;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return res;
    }

}
