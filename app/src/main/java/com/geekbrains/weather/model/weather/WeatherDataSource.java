package com.geekbrains.weather.model.weather;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.geekbrains.weather.database.DatabaseHelper;
import com.geekbrains.weather.model.note.Note;
import com.geekbrains.weather.model.note.NoteDataReader;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by shkryaba on 04/08/2018.
 */

public class WeatherDataSource implements Closeable {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private WeatherDataReader weatherDataReader;

    public WeatherDataSource(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
        weatherDataReader = new WeatherDataReader(database);
        weatherDataReader.open();
    }

    @Override
    public void close() throws IOException {
        weatherDataReader.close();
        databaseHelper.close();
    }

    public WeatherModel addWeatherItem(String temperature, String pressure, String humidity, String datetime) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_WEATHER_DATETIME, datetime);
        values.put(DatabaseHelper.COLUMN_WEATHER_TEMPERATURE, temperature);
        values.put(DatabaseHelper.COLUMN_WEATHER_HUMIDITY, humidity);
        values.put(DatabaseHelper.COLUMN_WEATHER_PRESSURE, pressure);

        long insertId = database.insert(DatabaseHelper.TABLE_WEATHER, null, values);

        WeatherModel newItem = new WeatherModel();
        newItem.setId(insertId);
        newItem.setDatetime(datetime);
        newItem.setHumidity(humidity);
        newItem.setPressure(pressure);
        newItem.setTemperature(temperature);
        return newItem;
    }

    public void deleteItem(WeatherModel item) {
        long id = item.getId();
        database.delete(DatabaseHelper.TABLE_WEATHER, DatabaseHelper.COLUMN_ID +
        " = " + id, null);
    }

    public void deleteAll() {
        database.delete(DatabaseHelper.TABLE_WEATHER, null, null);
    }

    public WeatherDataReader getWeatherDataReader() {
        return weatherDataReader;
    }

    public DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }

}
