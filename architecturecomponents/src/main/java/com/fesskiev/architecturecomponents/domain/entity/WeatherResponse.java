
package com.fesskiev.architecturecomponents.domain.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "weather_response")
public class WeatherResponse extends DBEntity {

    @Ignore
    @SerializedName("city")
    private City city;

    @Ignore
    @SerializedName("list")
    private List<WeatherDesc> weatherList = new ArrayList<>();

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<WeatherDesc> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<WeatherDesc> weatherList) {
        this.weatherList = weatherList;
    }

    @Override
    public String toString() {
        return "WeatherResponse{" +
                ", city=" + city +
                ", weatherList=" + weatherList +
                '}';
    }
}
