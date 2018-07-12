package com.geekbrains.weather;

import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.ArrayList;
import java.util.List;

public class DataSourceBuilder {
    private List<WeatherItem> dataSource;
    private Resources resources;

    public DataSourceBuilder(Resources resources) {
        dataSource = new ArrayList<>(6);
        this.resources = resources;
    }

    public List<WeatherItem> build() {
        String[] dates = resources.getStringArray(R.array.dates);
        String[] temperatures = resources.getStringArray(R.array.temperatures);
        int[] pictures = getImageArray();
        for (int i = 0; i < dates.length; i++) {
            dataSource.add(new WeatherItem(pictures[i], dates[i], temperatures[i]));
        }
        return dataSource;
    }

    private int[] getImageArray() {
        TypedArray pictures = resources.obtainTypedArray(R.array.pictures);
        int length = pictures.length();
        int[] answer = new int[length];
        for (int i = 0; i < length; i++) {
            answer[i] = pictures.getResourceId(i,0);
        }
        return answer;
    }
}
