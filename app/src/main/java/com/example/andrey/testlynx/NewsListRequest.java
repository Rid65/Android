package com.example.andrey.testlynx;

import com.example.andrey.testlynx.model.Events;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsListRequest  {
    @GET("list.php")
    Call<Events> loadNewsList(@Query("category") String category);
}
