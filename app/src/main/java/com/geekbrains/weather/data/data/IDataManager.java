package com.geekbrains.weather.data.data;

import android.content.Context;

/**
 * Created by shkryaba on 01/08/2018.
 */

public interface IDataManager {
    void initRetrofit();

    void requestRetrofit(String city, String keyApi);

    void setContext(Context context);
}
