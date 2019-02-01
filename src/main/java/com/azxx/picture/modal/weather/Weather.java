package com.azxx.picture.modal.weather;

public class Weather {

    private String date;
    private String temphigh;
    private String templow;
    private String wind;
    private String weather;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemphigh() {
        return temphigh;
    }

    public void setTemphigh(String temphigh) {
        this.temphigh = temphigh;
    }

    public String getTemplow() {
        return templow;
    }

    public void setTemplow(String templow) {
        this.templow = templow;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "date='" + date + '\'' +
                ", temphigh='" + temphigh + '\'' +
                ", templow='" + templow + '\'' +
                ", wind='" + wind + '\'' +
                ", weather='" + weather + '\'' +
                '}';
    }
}
