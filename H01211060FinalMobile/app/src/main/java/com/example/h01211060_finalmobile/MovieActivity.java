package com.example.h01211060_finalmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Movie;
import android.os.Bundle;

import com.example.h01211060_finalmobile.fragments.DetailFragment;
import com.example.h01211060_finalmobile.model.MovieModel;

public class MovieActivity extends AppCompatActivity {
    public static final String ARG_MOVIE = "movie";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        if (getIntent() != null) {
            MovieModel movie = getIntent().getParcelableExtra(ARG_MOVIE);
            showDetailFragment(movie);
        }
    }

    private void showDetailFragment(MovieModel movie) {
        DetailFragment fragment = DetailFragment.newInstance(movie);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}