package com.fesskiev.architecturecomponents.domain.source.remote;


import android.content.Context;

import com.fesskiev.architecturecomponents.R;
import com.fesskiev.architecturecomponents.domain.entity.City;
import com.fesskiev.architecturecomponents.domain.entity.WeatherDesc;
import com.fesskiev.architecturecomponents.domain.entity.WeatherResponse;
import com.fesskiev.architecturecomponents.domain.source.DataSource;
import com.fesskiev.architecturecomponents.domain.source.remote.retrofit.APIService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Retrofit;

public class RemoteDataSource implements DataSource {

    private APIService service;
    private Context context;

    public RemoteDataSource(Context context, Retrofit retrofit) {
        this.context = context;
        service = retrofit.create(APIService.class);
    }

    @Override
    public Single<WeatherResponse> getWeatherByCity(String city) {
        return service.getWeatherByCity(weatherQueryParams(city));
    }

    @Override
    public Observable<WeatherResponse> getWeatherByLocation(double latitude, double longitude) {
        return service.getWeatherByLocation(weatherLocationQueryParams(latitude, longitude));
    }

    private Map<String, String> weatherLocationQueryParams(double latitude, double longitude) {
        Map<String, String> data = new LinkedHashMap<>();
        data.put("lat", String.valueOf(latitude));
        data.put("lon", String.valueOf(longitude));
        data.put("mode", "json");
        data.put("units", "metric");
        data.put("cnt", "1");
        data.put("apikey", context.getString(R.string.open_weather_key));
        return data;
    }

    private Map<String, String> weatherQueryParams(String city) {
        Map<String, String> data = new LinkedHashMap<>();
        data.put("q", city);
        data.put("mode", "json");
        data.put("units", "metric");
        data.put("cnt", "7");
        data.put("apikey", context.getString(R.string.open_weather_key));
        return data;
    }

    @Override
    public void saveWeatherResponse(WeatherResponse weatherResponse) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Single<List<City>> getCities() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Single<List<WeatherDesc>> getWeather(String cityId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Single<Object> deleteCity(City city) {
        throw new UnsupportedOperationException();
    }
}
