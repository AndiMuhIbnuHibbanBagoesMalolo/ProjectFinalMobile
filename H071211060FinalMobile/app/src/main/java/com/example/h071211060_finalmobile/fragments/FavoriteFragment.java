package com.example.h071211060_finalmobile.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.h071211060_finalmobile.R;
import com.example.h071211060_finalmobile.adapter.FavoriteAdapter;
import com.example.h071211060_finalmobile.helper.DatabaseHelper;
import com.example.h071211060_finalmobile.models.movie.MovieResults;
import com.example.h071211060_finalmobile.models.tvshows.TVShowsResults;

import java.util.List;

public class FavoriteFragment extends Fragment {

    private RecyclerView favRV;
    private FavoriteAdapter favoriteAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        favRV = view.findViewById(R.id.favRV);
        favRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        favoriteAdapter = new FavoriteAdapter(getContext());
        favRV.setAdapter(favoriteAdapter);

        databaseHelper = new DatabaseHelper(getContext());

        favoriteAdapter.fetchFavoriteMovies();
        favoriteAdapter.fetchFavoriteTVShows();

        return view;
    }
}