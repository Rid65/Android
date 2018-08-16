package com.geekbrains.weather.retrofit;

import com.geekbrains.weather.model.weather.WeatherRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by shkryaba on 01/08/2018.
 */

public interface OpenWeather {

    @GET("/data/2.5/weather")
    Call<WeatherRequest> loadWeather(@Query("lat") String latitude, @Query("lon") String longitude, @Query("appid") String keyApi);
}
