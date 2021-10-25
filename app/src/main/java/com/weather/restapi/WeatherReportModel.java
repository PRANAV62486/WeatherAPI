package com.weather.restapi;

public class WeatherReportModel {

    //Here I'm not using all the available values rather I'm using only the required values
    String weather_state,low,high,current,wind_direction,date;

    public WeatherReportModel() {
    }

    @Override
    public String toString() {
        return "Date = " +date+'\''+
                "weather_state='" + weather_state + '\'' +
                ", low='" + low + '\'' +
                ", high='" + high + '\'' +
                ", current='" + current + '\'' +
                ", wind_direction='" + wind_direction + '\'';
    }

}