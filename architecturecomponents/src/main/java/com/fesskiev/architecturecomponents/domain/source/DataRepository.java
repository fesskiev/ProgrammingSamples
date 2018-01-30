package com.fesskiev.architecturecomponents.domain.source;


import com.fesskiev.architecturecomponents.domain.entity.City;
import com.fesskiev.architecturecomponents.domain.entity.WeatherDesc;
import com.fesskiev.architecturecomponents.domain.entity.WeatherResponse;
import com.fesskiev.architecturecomponents.domain.source.local.LocalDataSource;
import com.fesskiev.architecturecomponents.domain.source.remote.RemoteDataSource;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public class DataRepository implements DataSource {

    private RemoteDataSource remoteDataSource;
    private LocalDataSource localDataSource;

    private boolean isNetworkAvailable;

    public DataRepository(RemoteDataSource remoteDataSource, LocalDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    @Override
    public Single<WeatherResponse> getWeatherByCity(String city) {
        if (isNetworkAvailable) {
            return remoteDataSource.getWeatherByCity(city).flatMap(weatherResponse -> {
                saveWeatherResponse(weatherResponse);
                return Single.just(weatherResponse);
            });
        } else {
            return localDataSource.getWeatherByCity(city);
        }
    }

    @Override
    public Observable<WeatherResponse> getWeatherByLocation(double latitude, double longitude) {
        if (isNetworkAvailable) {
            return remoteDataSource.getWeatherByLocation(latitude, longitude);
        } else {
            return Observable.error(new IOException());
        }
    }

    @Override
    public Single<List<City>> getCities() {
        return localDataSource.getCities();
    }

    @Override
    public Single<List<WeatherDesc>> getWeather(String cityId) {
        return localDataSource.getWeather(cityId);
    }

    @Override
    public void saveWeatherResponse(WeatherResponse weatherResponse) {
        localDataSource.saveWeatherResponse(weatherResponse);
    }

    @Override
    public Single<Object> deleteCity(City city) {
       return localDataSource.deleteCity(city);
    }

    public boolean isNetworkAvailable() {
        return isNetworkAvailable;
    }

    public void setNetworkAvailable(boolean networkAvailable) {
        isNetworkAvailable = networkAvailable;
    }

}
