package com.azxx.picture.modal.weather;

import java.util.List;

public class TodayWeather {


    private Weather yesterday;
    private String life;
    private String temp;
    private String aqi;
    private String city;
    private List<Weather> forecast;

    public Weather getYesterday() {
        return yesterday;
    }

    public void setYesterday(Weather yesterday) {
        this.yesterday = yesterday;
    }

    public String getLife() {
        return life;
    }

    public void setLife(String life) {
        this.life = life;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Weather> getForecast() {
        return forecast;
    }

    public void setForecast(List<Weather> forecast) {
        this.forecast = forecast;
    }

    @Override
    public String toString() {
        return "TodayWeather{" +
                "yesterday=" + yesterday +
                ", life='" + life + '\'' +
                ", temp='" + temp + '\'' +
                ", aqi='" + aqi + '\'' +
                ", city='" + city + '\'' +
                ", forecast=" + forecast +
                '}';
    }
}
