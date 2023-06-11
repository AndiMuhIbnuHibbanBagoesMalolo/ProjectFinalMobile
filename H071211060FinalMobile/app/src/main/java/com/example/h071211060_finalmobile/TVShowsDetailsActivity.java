package com.example.h071211060_finalmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.h071211060_finalmobile.R;
import com.example.h071211060_finalmobile.helper.DatabaseHelper;
import com.example.h071211060_finalmobile.models.movie.MovieResults;
import com.example.h071211060_finalmobile.models.tvshows.TVShowsResults;

import java.util.Objects;

public class TVShowsDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_TV_SHOW = "extra_tv_show";
    String televTitle, televDate, televDesc, televPp, televBp;
    Double televRate;
    ImageView tvLogo, tvProf, tvBp, tvFav, tvBackbt;
    TextView tvtitle, tvDate, tvrate, tvdesc;
    TVShowsResults tvresult;

    DatabaseHelper databaseHelper;
    boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshows_details);

        databaseHelper = new DatabaseHelper(this);

        tvLogo = findViewById(R.id.telev_logo);
        tvProf = findViewById(R.id.telev_Poster);
        tvBp = findViewById(R.id.telev_bdrop);
        tvFav = findViewById(R.id.telev_favbt);
        tvBackbt = findViewById(R.id.telev_backbt);

        tvtitle = findViewById(R.id.telev_title);
        tvDate = findViewById(R.id.telev_date);
        tvrate = findViewById(R.id.telev_rate);
        tvdesc = findViewById(R.id.telev_desc);

        tvresult = getIntent().getParcelableExtra(EXTRA_TV_SHOW);
        televTitle = tvresult.getName();
        televDesc = tvresult.getOverview();
        televDate = tvresult.getFirstAirDate();
        televRate = tvresult.getVoteAverage();
        televPp = tvresult.getPosterPath();
        televBp = tvresult.getBackdropPath();

        if (televTitle != null) {
            tvtitle.setText(televTitle);
        } else {
            tvtitle.setText("N/A");
        }
        if (televDate != null) {
            tvDate.setText(televDate);
        } else {
            tvDate.setText("N/A");
        }
        if (televRate != null) {
            tvrate.setText(String.valueOf(televRate));
        } else {
            tvrate.setText("N/A");
        }
        if (televDesc != null) {
            tvdesc.setText(televDesc);
        } else {
            tvdesc.setText("N/A");
        }

        Glide.with(getApplicationContext())
                .load("https://image.tmdb.org/t/p/w185" + televPp)
                .into(tvProf);
        Glide.with(getApplicationContext())
                .load("https://image.tmdb.org/t/p/original" + televBp)
                .into(tvBp);

        if (televTitle != null) {
            isFavorite = databaseHelper.isFavorite(televTitle, "tv_show");
        }

        if (isFavorite) {
            tvFav.setImageResource(R.drawable.baseline_favorite_24);
        } else {
            tvFav.setImageResource(R.drawable.baseline_favorite_border_24);
        }

        tvFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavorite) {
                    databaseHelper.removeFavorite(televTitle, "tv_show");
                    tvFav.setImageResource(R.drawable.baseline_favorite_border_24);
                    isFavorite = false;
                    Toast.makeText(TVShowsDetailsActivity.this, televTitle + " Dihapus dari favorite", Toast.LENGTH_SHORT).show();
                } else {
                    databaseHelper.addFavorite(televTitle, "tv_show", televPp, televDate, televDesc, televBp, televRate);
                    tvFav.setImageResource(R.drawable.baseline_favorite_24);
                    isFavorite = true;
                    Toast.makeText(TVShowsDetailsActivity.this, televTitle + " Ditambahkan ke favorite", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvBackbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}