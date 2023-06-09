package com.example.h01211060_finalmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.h01211060_finalmobile.fragments.FavoriteFragment;
import com.example.h01211060_finalmobile.fragments.MovieFragment;
import com.example.h01211060_finalmobile.fragments.TVShowsFragment;

public class MainActivity extends AppCompatActivity {
    ImageView movie, tvshows,favorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movie = findViewById(R.id.tabMovie);
        tvshows = findViewById(R.id.tabTVShows);
        favorite = findViewById(R.id.tabFavorite);

        replaceFragment(new MovieFragment());
        movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new MovieFragment());
            }
        });
        tvshows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new TVShowsFragment());
            }
        });
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { replaceFragment(new FavoriteFragment()); }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }
}