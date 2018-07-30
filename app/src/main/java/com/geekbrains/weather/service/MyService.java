package com.geekbrains.weather.service;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.widget.Toast;

import com.geekbrains.weather.ui.base.BaseActivity;

import java.util.List;

import static android.app.Service.START_NOT_STICKY;

/**
 * Created by shkryaba on 27.07.18.
 */
public class MyService extends Service {
    IBinder mBinder; // Интерфейс связи с клиентом
    private SensorManager sensorManager;
    private List<Sensor> listSensors;
    private Sensor sensorHumidity;
    private Sensor sensorTemperature;

    SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY)
                writeData(sensorEvent.values, Sensor.TYPE_RELATIVE_HUMIDITY);
            else if (sensorEvent.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE)
                writeData(sensorEvent.values, Sensor.TYPE_AMBIENT_TEMPERATURE);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };


    @Override
    public void onCreate() {
        // Служба создается
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Служба стартовала
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorHumidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        sensorTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorManager.registerListener(listener, sensorHumidity, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(listener, sensorTemperature, SensorManager.SENSOR_DELAY_NORMAL);

        return START_NOT_STICKY;
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

    private void writeData(float[] values, int sensorType) {
        try {
            Intent intent = new Intent(BaseActivity.BROADCAST_ACTION);
            if (sensorType == Sensor.TYPE_RELATIVE_HUMIDITY)
                intent.putExtra(BaseActivity.SENSOR_HUMIDITY, values[0]);
            else if (sensorType == Sensor.TYPE_AMBIENT_TEMPERATURE)
                intent.putExtra(BaseActivity.SENSOR_TEMPERATURE, values[0]);
            sendBroadcast(intent);
        } catch (Throwable t1) {
            Toast.makeText(getApplicationContext(),
                    "Exception: " + t1.toString(), Toast.LENGTH_SHORT)
                    .show();
        }
    }

}