package com.example.h01211060_finalmobile.fragments;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.h01211060_finalmobile.MovieActivity;
import com.example.h01211060_finalmobile.R;
import com.example.h01211060_finalmobile.adapter.MovieAdapter;
import com.example.h01211060_finalmobile.model.MovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieFragment extends Fragment {
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private List<MovieModel> movieList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        movieAdapter = new MovieAdapter(movieList, new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MovieModel movie) {
            }
        });
        recyclerView.setAdapter(movieAdapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchMovies();
    }

    private void fetchMovies() {
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        String baseUrl = "https://www.themoviedb.org/movie/now-playing";
        String apiKey = "36bd97dc9db63b7eb716590909ad7496";
        String url = baseUrl + "?api_key=" + apiKey;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject movieObject = results.getJSONObject(i);
                                int id = movieObject.getInt("id");
                                String title = movieObject.getString("title");
                                String posterPath = movieObject.getString("poster_path");
                                String releaseDate = movieObject.getString("release_date");
                                double voteAverage = movieObject.getDouble("vote_average");
                                String overview = movieObject.getString("overview");
                                String backdropPath = movieObject.getString("backdrop_path");
                                MovieModel movie = new MovieModel(id, title, posterPath, releaseDate, voteAverage, overview, backdropPath);
                                movieList.add(movie);
                            }
                            movieAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Penanganan kesalahan jika permintaan gagal
                        Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(request);
    }

    private void showDetailActivity(Movie movie) {
        Intent intent = new Intent(requireContext(), MovieActivity.class);
        intent.putExtra(MovieActivity.ARG_MOVIE, "movie");
        startActivity(intent);
    }

    private void logMovieUrls(List<MovieModel> movieList) {
        for (MovieModel movie : movieList) {
            Log.d("URL", "Poster Path: " + movie.getPosterPath());
            Log.d("URL", "Backdrop Path: " + movie.getBackdropPath());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        logMovieUrls(movieList);
    }
}