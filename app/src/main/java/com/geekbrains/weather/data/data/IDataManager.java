package com.geekbrains.weather.data.data;

import android.content.Context;

/**
 * Created by shkryaba on 01/08/2018.
 */

public interface IDataManager {
    void initRetrofit();
    void setContext(Context context);
    void requestRetrofit(String lat, String lon, String keyApi);
}
