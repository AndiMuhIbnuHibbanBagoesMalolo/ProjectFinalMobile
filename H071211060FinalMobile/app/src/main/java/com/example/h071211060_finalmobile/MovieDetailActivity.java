package com.example.h071211060_finalmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.h071211060_finalmobile.R;
import com.example.h071211060_finalmobile.helper.DatabaseHelper;
import com.example.h071211060_finalmobile.fragments.MovieFragment;
import com.example.h071211060_finalmobile.models.movie.MovieResults;

import java.util.Objects;
public class MovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    String MovieTitle , MovieDate, MovieDesc, MoviePp, MovieBp;
    Double MovieRate;
    ImageView movieLogo, movProf, movBp, movieFavoriteBtn, movieBtnBack;
    TextView movieTitle, movieDate, movieRate, movieDesc;
    MovieResults MovieResults;
    DatabaseHelper databaseHelper;
    boolean isFavorite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        databaseHelper = new DatabaseHelper(this);


        movieLogo = findViewById(R.id.mov_logo);
        movProf = findViewById(R.id.Mov_Poster);
        movBp = findViewById(R.id.mov_bdrop);
        movieFavoriteBtn= findViewById(R.id.mov_favbt);
        movieBtnBack= findViewById(R.id.mov_backbt);
        movieTitle = findViewById(R.id.mov_title);
        movieDate = findViewById(R.id.mov_date);
        movieRate= findViewById(R.id.move_rate);
        movieDesc= findViewById(R.id.mov_desc);
        MovieResults = getIntent().getParcelableExtra(EXTRA_MOVIE);
        MovieTitle = MovieResults.getTitle();
        MovieDesc = MovieResults.getOverview();
        MovieDate = MovieResults.getReleaseDate();
        MovieRate = MovieResults.getVoteAverage();
        MoviePp = MovieResults.getPosterPath();
        MovieBp = MovieResults.getBackdropPath();
        movieTitle.setText(MovieTitle);
        movieDate.setText(MovieDate);
        movieRate.setText(String.valueOf(MovieRate));
        movieDesc.setText(MovieDesc);

        Glide.with(getApplicationContext())
                .load("https://image.tmdb.org/t/p/w185"+ MoviePp)
                .into(movProf);
        Glide.with(getApplicationContext())
                .load("https://image.tmdb.org/t/p/original"+ MovieBp)
                .into(movBp);

        if (MovieTitle != null) {
            isFavorite = databaseHelper.isFavorite(MovieTitle, "movie");
        }

        if (isFavorite) {
            movieFavoriteBtn.setImageResource(R.drawable.baseline_favorite_24);
        } else {
            movieFavoriteBtn.setImageResource(R.drawable.baseline_favorite_border_24);
        }

        movieFavoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavorite) {
                    databaseHelper.removeFavorite(MovieTitle, "movie");
                    movieFavoriteBtn.setImageResource(R.drawable.baseline_favorite_border_24);
                    isFavorite = false;
                    Toast.makeText(MovieDetailActivity.this, MovieTitle + " Dihapus dari favorite", Toast.LENGTH_SHORT).show();
                } else {
                    databaseHelper.addFavorite(MovieTitle, "movie", MoviePp, MovieDate, MovieDesc, MovieBp, MovieRate);
                    movieFavoriteBtn.setImageResource(R.drawable.baseline_favorite_24);
                    isFavorite = true;
                    Toast.makeText(MovieDetailActivity.this, MovieTitle + " Ditambahkan di favorite", Toast.LENGTH_SHORT).show();
                }
            }
        });

        movieBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}