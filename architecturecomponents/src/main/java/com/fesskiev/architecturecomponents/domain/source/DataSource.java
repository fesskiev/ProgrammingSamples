package com.fesskiev.architecturecomponents.domain.source;


import com.fesskiev.architecturecomponents.domain.entity.City;
import com.fesskiev.architecturecomponents.domain.entity.WeatherDesc;
import com.fesskiev.architecturecomponents.domain.entity.WeatherResponse;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface DataSource {

    Single<WeatherResponse> getWeatherByCity(String city);

    Observable<WeatherResponse> getWeatherByLocation(double latitude, double longitude);

    Single<List<City>> getCities();

    Single<List<WeatherDesc>> getWeather(String cityId);

    void saveWeatherResponse(WeatherResponse weatherResponse);

    Single<Object> deleteCity(City city);
}
