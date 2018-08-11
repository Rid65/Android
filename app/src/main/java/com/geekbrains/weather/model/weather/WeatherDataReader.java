package com.geekbrains.weather.model.weather;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import com.geekbrains.weather.database.DatabaseHelper;
import com.geekbrains.weather.model.note.Note;

import java.io.Closeable;

/**
 * Created by shkryaba on 04/08/2018.
 */

public class WeatherDataReader implements Closeable {

    private Cursor cursor;

    private SQLiteDatabase database;

    private String[] allColumn = {
            DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_WEATHER_DATETIME,
            DatabaseHelper.COLUMN_WEATHER_TEMPERATURE,
            DatabaseHelper.COLUMN_WEATHER_HUMIDITY,
            DatabaseHelper.COLUMN_WEATHER_PRESSURE
    };

    public WeatherDataReader(SQLiteDatabase database) {
        this.database = database;
    }

    public void open() {
        query();
        cursor.moveToFirst();
    }

    private void query() {
        cursor = database.query(DatabaseHelper.TABLE_WEATHER, allColumn, null, null, null, null, null);
    }

    public void close() {
        cursor.close();
    }

    public WeatherModel getPosition(int pos) {
        cursor.moveToPosition(pos);
        return cursorToNote();
    }

    public WeatherModel getWeatherItemByDate(String date) {
//        cursor = database.execSQL("SELECT * FROM " + DatabaseHelper.TABLE_WEATHER + " WHERE " + DatabaseHelper.COLUMN_WEATHER_DATETIME + "=" + date);
        String[] selectionArgs = {date};
        cursor = database.query(
                DatabaseHelper.TABLE_WEATHER,
                allColumn,
                DatabaseHelper.COLUMN_WEATHER_DATETIME + " = ?",
                selectionArgs,
                null,
                null,
                null);
        cursor.moveToFirst();
        return cursorToNote();
    }

    public int getCount() {
        return cursor.getCount();
    }

    private WeatherModel cursorToNote() {
       WeatherModel item = new WeatherModel();
        item.setId(cursor.getLong(0));
        item.setDatetime(cursor.getString(1));
        item.setTemperature(cursor.getString(2));
        item.setHumidity(cursor.getString(3));
        item.setPressure(cursor.getString(4));
       return item;
    }

    public void Refresh() {
        int pos = cursor.getPosition();
        query();
        cursor.moveToPosition(pos);
    }
}
