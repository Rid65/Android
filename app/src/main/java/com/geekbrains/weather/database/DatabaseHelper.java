package com.geekbrains.weather.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shkryaba on 04/08/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notes.db";
    public static final int DATABASE_VERSION = 2;

    public static final String TABLE_NOTES = "notes";
    public static final String TABLE_WEATHER = "weather";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_NOTE_TITLE = "title";
    public static final String COLUMN_NOTE_DATETIME = "note_date";
    public static final String COLUMN_WEATHER_DATETIME = "weather_date";
    public static final String COLUMN_WEATHER_TEMPERATURE = "temperature";
    public static final String COLUMN_WEATHER_PRESSURE = "pressure";
    public static final String COLUMN_WEATHER_HUMIDITY = "humidity";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NOTES + " (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NOTE + " TEXT," +
                COLUMN_NOTE_TITLE + " TEXT," + COLUMN_NOTE_DATETIME + " DATE);");
        createWeatherTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if ((oldVersion == 1) && (newVersion == 2)) {
            String upgradeQuery = "ALTER TABLE " + TABLE_NOTES + " ADD COLUMN" +
                    COLUMN_NOTE_TITLE + " TEXT DEFAULT Title";
            sqLiteDatabase.execSQL(upgradeQuery);
        }
    }

    public void createWeatherTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_WEATHER + " (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_WEATHER_DATETIME + " DATE," +
                COLUMN_WEATHER_TEMPERATURE + " VARCHAR(5)," + COLUMN_WEATHER_HUMIDITY + " VARCHAR(5)," + COLUMN_WEATHER_PRESSURE + " VARCHAR(5));");
    }
}
