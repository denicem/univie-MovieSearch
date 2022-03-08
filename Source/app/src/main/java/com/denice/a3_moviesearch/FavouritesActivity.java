package com.denice.a3_moviesearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity {

    private RecyclerView favView;
    private FavAdapter favAdapter;
    private RecyclerView.LayoutManager favLayoutManager;

    public static ArrayList<Favourite> favList = new ArrayList<>();
    public static void addFav (Favourite f) {
        favList.add(f);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        if (!favList.isEmpty()) {
            TextView nothingText = findViewById(R.id.nothingText);
            nothingText.setVisibility(View.GONE);
        }

        //will display all favourite elements from the Array 'favList'
        favView = findViewById(R.id.favList);
        favView.setHasFixedSize(true);
        favLayoutManager = new LinearLayoutManager(this);
        favAdapter = new FavAdapter(favList);

        favView.setLayoutManager(favLayoutManager);
        favView.setAdapter(favAdapter);

        favAdapter.setOnItemClickListener(new FavAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(FavouritesActivity.this, MovieSearchActivity.class);
                intent.putExtra(MainActivity.MOVIE_DATA_MESSAGE, favList.get(position).getJsonFile());
                startActivity(intent);
            }
        });
    }
}
