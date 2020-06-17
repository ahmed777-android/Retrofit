package com.example.retrofit.servic;

import com.example.retrofit.pojo.Movie;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface GitHubService {

@GET("movie/popular")
Call<Movie> getPopularMovie(@Query("api_key") String api_key , @QueryMap Map<String,String> pageId);



}
