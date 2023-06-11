package com.example.h071211060_finalmobile.interfacee;

import com.example.h071211060_finalmobile.models.tvshows.TVShowsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TVShowsInterface {
    @GET("/3/tv/{category}")
    Call<TVShowsResponse> getTelev(
            @Path("category") String category,
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") int page
    );
}
