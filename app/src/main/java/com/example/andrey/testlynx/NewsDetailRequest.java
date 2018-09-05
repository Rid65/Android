package com.example.andrey.testlynx;

import com.example.andrey.testlynx.model.Events;
import com.example.andrey.testlynx.model.NewsDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsDetailRequest {
    @GET("post.php")
    Call<NewsDetail> loadNewsDetail(@Query("article") String article);
}
