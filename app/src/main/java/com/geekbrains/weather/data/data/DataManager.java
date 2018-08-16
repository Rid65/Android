package com.geekbrains.weather.data.data;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.geekbrains.weather.model.weather.WeatherRequest;
import com.geekbrains.weather.retrofit.OpenWeather;
import com.geekbrains.weather.ui.base.BaseActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shkryaba on 01/08/2018.
 */

public class DataManager implements IDataManager {

    private static final String BASE_URL = "https://api.openweathermap.org/";
    private static final String TAG = "DataManager";
    private OpenWeather openWeather;
    private OnResponseCompleted responseCompleted;
    private Context context;

    @Override
    public void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        openWeather = retrofit.create(OpenWeather.class);
    }

    @Override
    public void requestRetrofit(String lat, String lon, String keyApi) {
        openWeather.loadWeather(lat, lon, keyApi).enqueue(new Callback<WeatherRequest>() {
            @Override
            public void onResponse(Call<WeatherRequest> call, Response<WeatherRequest> response) {
                if (response != null && response.body() != null) {
                    sendBroadcastRequest(response.body().getMain().getTemp().toString(),
                            response.body().getCoord().getLat().toString(),
                            response.body().getCoord().getLon().toString());
                    Log.d(TAG, response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<WeatherRequest> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    @Override
    public  void setContext(Context context) {
        this.context = context;
    }


    private void sendBroadcastRequest(String temperature, String lat, String lon) {
        try {
            Intent intent = new Intent(BaseActivity.BROADCAST_ACTION);
            intent.putExtra(BaseActivity.TEMP_VAL, temperature);
            intent.putExtra("LAT", lat);
            intent.putExtra("LON", lon);
            context.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOnResponseCompleted(OnResponseCompleted responseCompleted) {
        this.responseCompleted = responseCompleted;
    }

    public interface OnResponseCompleted {
        void onCompleted(WeatherRequest weatherRequest);
    }
}
