package com.geekbrains.weather;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class WeatherFragment extends BaseFragment implements CreateActionFragment.OnHeadlineSelectedListener {

    private static final String ARG_COUNTRY = "ARG_COUNTRY";
    private String country;
    private TextView textView;
    private TextView tv_humidity;
    private TextView tv_pressure;
    private TextView tv_temperature;

    private Sensor sensorTemperature;
    private Sensor sensorHumidity;
    private SensorManager sensorManager;

    SensorEventListener listenerHumidity = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            showHumiditySensor(sensorEvent);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    SensorEventListener listenerTemperature = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            showTemperatureSensor(sensorEvent);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    public WeatherFragment() {
//        Особенностью поведения android-а состоит в том, что в любой момент
//        он может убить конкретный фрагмент (с случаи нехватки памяти например)
//        и потом попытаться восстановить его, используя конструктор без параметров,
//                следовательно передача параметров через конструкторы черевата
//        крэшами приложения в произвольный момент времени.
    }

    public static WeatherFragment newInstance(String country) {
//        Для того что бы положить требуемые значения во фрагмент,
//        нужно обернуть их в Bundle и передать через метод setArguments.
//        Стандартным способом передачи параметров считается создание статического
//        метода newInstance (...),
//        а для восстановление параметров используется метод getArguments(...),вызываемый в
//        методе жизненного цикла onCreate (...) .
        Bundle args = new Bundle();
        args.putString(ARG_COUNTRY, country);
        WeatherFragment fragment = new WeatherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            country = getArguments().getString(ARG_COUNTRY);
        }
        initSensors();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.weather_layout, container, false);
    }

    @Override
    protected void initLayout(View view, Bundle savedInstanceState) {
        textView = view.findViewById(R.id.tv_country);
        tv_humidity = view.findViewById(R.id.tv_humidity);
        tv_pressure = view.findViewById(R.id.tv_pressure);
        tv_temperature = view.findViewById(R.id.bigTemp);

        //проверяем нашу переменную если она не пустая показываем город, если наоборот - ничего не показываем
        if (country != null && country.length() > 0) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(country);
        } else {
            textView.setVisibility(View.GONE);
        }

        tv_pressure.setText("752mmHg");
        /*tv_humidity.setText("30%");
        ((TextView) getBaseActivity().findViewById(R.id.tv_pressure)).setText("752mmHg");*/
    }

    private void initSensors() {
        sensorManager = (SensorManager) getBaseActivity().getSystemService(Context.SENSOR_SERVICE);

        sensorTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorHumidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        if (sensorHumidity != null)
            sensorManager.registerListener(listenerHumidity, sensorHumidity, SensorManager.SENSOR_DELAY_NORMAL);
        if (sensorTemperature != null)
            sensorManager.registerListener(listenerTemperature, sensorTemperature, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void showHumiditySensor(SensorEvent event) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Math.round(event.values[0])).append("%");
        tv_humidity.setText(stringBuilder);
    }

    private void showTemperatureSensor(SensorEvent event) {
        StringBuilder stringBuilder = new StringBuilder();
        if (event.values[0] >= 0)
            stringBuilder.append("+").append(Math.round(event.values[0]));
        else
            stringBuilder.append("-").append(Math.round(event.values[0]));
        tv_temperature.setText(stringBuilder);
    }

    @Override
    public void onArticleSelected(ArrayList<String> citiesList) {
        textView.setVisibility(View.VISIBLE);
        String cities = citiesList.toString();
        textView.setText(cities.substring(cities.indexOf("[") + 1, cities.indexOf("]")));
    }
}
