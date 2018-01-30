package com.fesskiev.architecturecomponents.domain.source.remote.retrofit;


import com.fesskiev.architecturecomponents.domain.entity.WeatherResponse;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface APIService {

    @GET(".")
    Single<WeatherResponse> getWeatherByCity(@QueryMap Map<String, String> options);

    @GET(".")
    Observable<WeatherResponse> getWeatherByLocation(@QueryMap Map<String, String> options);

}
