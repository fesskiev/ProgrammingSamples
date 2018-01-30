package com.fesskiev.architecturecomponents.domain.source.local;


import com.fesskiev.architecturecomponents.domain.entity.City;
import com.fesskiev.architecturecomponents.domain.entity.Coordinate;
import com.fesskiev.architecturecomponents.domain.entity.Temperature;
import com.fesskiev.architecturecomponents.domain.entity.Weather;
import com.fesskiev.architecturecomponents.domain.entity.WeatherDesc;
import com.fesskiev.architecturecomponents.domain.entity.WeatherResponse;
import com.fesskiev.architecturecomponents.domain.source.DataSource;
import com.fesskiev.architecturecomponents.domain.source.local.room.WeatherDatabase;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public class LocalDataSource implements DataSource {

    enum Irrelevant { INSTANCE; }

    private WeatherDatabase database;

    public LocalDataSource(WeatherDatabase database) {
        this.database = database;
    }

    @Override
    public Single<WeatherResponse> getWeatherByCity(String city) {
        return Single.fromCallable(() -> {

            final WeatherResponse weatherResponse = new WeatherResponse();

            City c = database.getWeatherDAO().getCity(city);
            weatherResponse.setCity(c);

            Coordinate coordinate = database.getWeatherDAO().getCoordinate(c.getId());
            c.setCoordinate(coordinate);

            List<WeatherDesc> weatherLists = database.getWeatherDAO().getWeatherList(c.getId());
            for (WeatherDesc weatherList : weatherLists) {
                Temperature temperature = database.getWeatherDAO().getTemperature(weatherList.getId());
                weatherList.setTemp(temperature);

                List<Weather> weathers = database.getWeatherDAO().getWeather(weatherList.getId());
                weatherList.setWeather(weathers);
            }
            weatherResponse.setWeatherList(weatherLists);

            return weatherResponse;
        });
    }

    @Override
    public Observable<WeatherResponse> getWeatherByLocation(double latitude, double longitude) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void saveWeatherResponse(WeatherResponse weatherResponse) {

        City city = weatherResponse.getCity();
        Coordinate coordinate = city.getCoordinate();
        coordinate.setCityId(city.getId());

        database.getWeatherDAO().insertCity(city);
        database.getWeatherDAO().insertCoordinate(coordinate);

        List<WeatherDesc> weatherLists = weatherResponse.getWeatherList();
        for (WeatherDesc weatherList : weatherLists) {
            weatherList.setCityId(city.getId());

            Temperature temperature = weatherList.getTemp();
            temperature.setListId(weatherList.getId());

            database.getWeatherDAO().insertWeatherList(weatherList);
            database.getWeatherDAO().insertTemperature(temperature);

            List<Weather> weathers = weatherList.getWeather();
            for (Weather weather : weathers) {
                weather.setListId(weatherList.getId());

                database.getWeatherDAO().insertWeather(weather);
            }
        }
    }

    @Override
    public Single<List<City>> getCities() {
        return Single.fromCallable(() -> {
            List<City> cities = database.getWeatherDAO().getCities();
            for (City city : cities) {
                Coordinate coordinate = database.getWeatherDAO().getCoordinate(city.getId());
                city.setCoordinate(coordinate);
            }
            return cities;
        });
    }

    @Override
    public Single<List<WeatherDesc>> getWeather(String cityId) {
        return Single.fromCallable(() -> {
            List<WeatherDesc> weatherLists = database.getWeatherDAO().getWeatherList(cityId);
            for (WeatherDesc weatherList : weatherLists) {
                Temperature temperature = database.getWeatherDAO().getTemperature(weatherList.getId());
                weatherList.setTemp(temperature);

                List<Weather> weathers = database.getWeatherDAO().getWeather(weatherList.getId());
                weatherList.setWeather(weathers);
            }
            return weatherLists;
        });
    }

    @Override
    public Single<Object> deleteCity(City city) {
        return Single.create(e -> {
            database.getWeatherDAO().delete(city);
            e.onSuccess(Irrelevant.INSTANCE);
        });
    }
}
