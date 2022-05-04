package com.example.apiweather.network;

import com.example.apiweather.model.Weather;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WeatherAPI {
    String API_URL = "https://dataservice.accuweather.com";
    String API_KEY = "?apikey=93Qg780lHwYM4SO58n7DFPLqHg4oKADn";
    String language = "&language=vi-vn";
    String metric = "&metric=true";

    @GET("/forecasts/v1/hourly/12hour/353412" + API_KEY + language + metric)
    Call<List<Weather>> getHourWeather();

    @GET("/forecasts/v1/daily/5day/353412" + API_KEY + language + metric)
    Call<List<Weather>> getDayWeather();
}

