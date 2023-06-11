package com.example.h071211060_finalmobile.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.h071211060_finalmobile.R;


import com.example.h071211060_finalmobile.adapter.TVShowsAdapter;
import com.example.h071211060_finalmobile.api.ApiClient;
import com.example.h071211060_finalmobile.interfacee.TVShowsInterface;
import com.example.h071211060_finalmobile.models.tvshows.TVShowsResponse;
import com.example.h071211060_finalmobile.models.tvshows.TVShowsResults;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVShowsFragment extends Fragment {

    private TVShowsAdapter adapter;
    String API_KEY = "36bd97dc9db63b7eb716590909ad7496";
    String LANGUAGE = "en-US";
    String CATEGORY = "top_rated";
    int PAGE = 1;
    private boolean isLoading = false;
    Context context;
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_t_v_shows, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.televRV);
        recyclerView.setHasFixedSize(true);

        context = requireContext();

        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        CallRetrofit();
    }


    private void CallRetrofit() {
        TVShowsInterface tvshowsInterface = ApiClient.getClient().create(TVShowsInterface.class);
        Call<TVShowsResponse> call = tvshowsInterface.getTelev(CATEGORY, API_KEY, LANGUAGE, PAGE);
        call.enqueue(new Callback<TVShowsResponse>() {
            @Override
            public void onResponse(Call<TVShowsResponse> call, Response<TVShowsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<TVShowsResults> tList = response.body().getResults();
                    adapter = new TVShowsAdapter(getContext(), tList);
                    recyclerView.setAdapter(adapter);
                } else {
                }
            }

            @Override
            public void onFailure(Call<TVShowsResponse> call, Throwable t) {
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                    PAGE++;
                    isLoading = true;
                    loadMoreData();
                }
            }
        });
    }
    private void loadMoreData() {
        TVShowsInterface tvshowsInterface = ApiClient.getClient().create(TVShowsInterface.class);
        Call<TVShowsResponse> call = tvshowsInterface.getTelev(CATEGORY, API_KEY, LANGUAGE, PAGE);
        call.enqueue(new Callback<TVShowsResponse>() {
            @Override
            public void onResponse(Call<TVShowsResponse> call, Response<TVShowsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<TVShowsResults> newList = response.body().getResults();
                    adapter.addTelevShows(newList);
                    isLoading = false;
                } else {
                }
            }
            @Override
            public void onFailure(Call<TVShowsResponse> call, Throwable t) {

            }
        });
    }
}