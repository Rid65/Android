package com.geekbrains.weather;

import java.util.Date;

public class WeatherItem {
    private int image;
    private String date;
    private String temperature;
    private String pressure;
    private String speedWind;

    public WeatherItem(int image, String date, String temperature, String pressure, String speedWind) {
        this.image = image;
        this.date = date;
        this.temperature = temperature;
        this.pressure = pressure;
        this.speedWind = speedWind;
    }

    public WeatherItem(int image, String date, String temperature) {
        this.image = image;
        this.date = date;
        this.temperature = temperature;
    }

    public int getImage() {
        return image;
    }

    public String getDate() {
        return date;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getPressure() {
        return pressure;
    }

    public String getSpeedWind() {
        return speedWind;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public void setSpeedWind(String speedWind) {
        this.speedWind = speedWind;
    }
}
