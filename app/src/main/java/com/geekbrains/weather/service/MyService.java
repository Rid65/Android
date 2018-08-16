package com.geekbrains.weather.service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.geekbrains.weather.data.data.DataManager;
import com.geekbrains.weather.data.data.IDataManager;
import com.geekbrains.weather.ui.base.BaseActivity;

import java.io.BufferedOutputStream;
import java.util.Calendar;
import java.util.List;

/**
 * Created by shkryaba on 27.07.18.
 */
public class MyService extends Service implements SensorEventListener {
    IBinder mBinder; // Интерфейс связи с клиентом
    private SensorManager sensorManager;
    private List<Sensor> listSensors;
    private Sensor sensor;
    private LocationManager locationManager;
    private String provider;
    private String lat;
    private String lon;

    @Override
    public void onCreate() {
        // Служба создается
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initSensors();
        requestLocation();
        initRetrofit();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Привязка клиента
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // Удаление привязки
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        // Перепривязка клиента
    }

    @Override
    public void onDestroy() {
        // Уничтожение службы
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            writeData(sensorEvent.values);
    }

    private void writeData(float[] values) {

        try {
            Intent intent = new Intent(BaseActivity.BROADCAST_ACTION);
            intent.putExtra(BaseActivity.SENSOR_VAL, values[0]);
            sendBroadcast(intent);
        } catch (Throwable t1) {
            Toast.makeText(getApplicationContext(),
                    "Exception: " + t1.toString(), Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void initSensors() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                sensorManager.SENSOR_DELAY_NORMAL);
    }

    private void initRetrofit() {
        IDataManager dataManager = new DataManager();
        dataManager.setContext(getBaseContext());
        dataManager.initRetrofit();
        dataManager.requestRetrofit(lat,lon, "b30878f9064492d82df68aa3f632f839");
    }

    private void requestLocation() {
        // Если пермиссии все таки нет - то просто выйдем, приложение не имеет смысла
        if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);

        // получим наиболее подходящий провайдер геолокации по критериям
        // Но можно и самому назначать какой провайдер использовать.
        // В основном это LocationManager.GPS_PROVIDER или LocationManager.NETWORK_PROVIDER
        // но может быть и LocationManager.PASSIVE_PROVIDER, это когда координаты уже кто-то недавно получил.
        provider = locationManager.getBestProvider(criteria, true);
        if (provider != null) {
            // Будем получать геоположение через каждые 10 секунд или каждые 10 метров
            locationManager.requestLocationUpdates(provider, 10000, 10, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    lat = Double.toString(location.getLatitude());  // Широта
                    lon = Double.toString(location.getLongitude());// Долгота
                    initRetrofit();
                }
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }
                @Override
                public void onProviderEnabled(String provider) {
                }
                @Override
                public void onProviderDisabled(String provider) {
                }
            });
        }
    }
}