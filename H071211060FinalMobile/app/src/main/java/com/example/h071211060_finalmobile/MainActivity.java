package com.example.h071211060_finalmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.h071211060_finalmobile.fragments.FavoriteFragment;
import com.example.h071211060_finalmobile.fragments.MovieFragment;
import com.example.h071211060_finalmobile.fragments.TVShowsFragment;

public class MainActivity extends AppCompatActivity {
    ImageView movie, tvShows, favorite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movie = findViewById(R.id.ivMovie);
        tvShows = findViewById(R.id.ivTV);
        favorite = findViewById(R.id.ivFavorite);

        FragmentManager fragmentManager = getSupportFragmentManager();
        MovieFragment homeFragment = new MovieFragment();
        Fragment fragment = fragmentManager.findFragmentByTag(MovieFragment.class.getSimpleName());
        if (!(fragment instanceof MovieFragment)) {
            fragmentManager.beginTransaction().add(R.id.container, homeFragment,
                    MovieFragment.class.getSimpleName()).commit();
        }

        movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchFragment(new MovieFragment());
            }
        });
        tvShows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchFragment(new TVShowsFragment());
            }
        });
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchFragment(new FavoriteFragment());
            }
        });
    }

    private void switchFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(fragment instanceof MovieFragment){
            transaction.replace(R.id.container, fragment, MovieFragment.class.getSimpleName()).commit();
        } else {
            transaction.replace(R.id.container, fragment, MovieFragment.class.getSimpleName()).addToBackStack(null).commit();
        }

    }
}