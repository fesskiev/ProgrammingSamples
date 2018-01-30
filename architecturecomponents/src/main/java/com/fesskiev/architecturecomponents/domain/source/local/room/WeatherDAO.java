package com.fesskiev.architecturecomponents.domain.source.local.room;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.fesskiev.architecturecomponents.domain.entity.City;
import com.fesskiev.architecturecomponents.domain.entity.Coordinate;
import com.fesskiev.architecturecomponents.domain.entity.Temperature;
import com.fesskiev.architecturecomponents.domain.entity.Weather;
import com.fesskiev.architecturecomponents.domain.entity.WeatherDesc;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface WeatherDAO {

    @Insert(onConflict = REPLACE)
    void insertWeatherList(WeatherDesc... weatherLists);

    @Insert(onConflict = REPLACE)
    void insertWeather(Weather... weather);

    @Insert(onConflict = REPLACE)
    void insertTemperature(Temperature... temperatures);

    @Insert(onConflict = REPLACE)
    void insertCoordinate(Coordinate... coordinates);

    @Insert(onConflict = REPLACE)
    void insertCity(City... city);

    @Delete
    void delete(City... city);


    @Query("SELECT * FROM city WHERE name LIKE :city")
    City getCity(String city);

    @Query("SELECT * FROM city")
    List<City> getCities();

    @Query("SELECT * FROM coordinate WHERE cityId LIKE :id")
    Coordinate getCoordinate(String id);

    @Query("SELECT * FROM weather_list WHERE cityId LIKE :id")
    List<WeatherDesc> getWeatherList(String id);

    @Query("SELECT * FROM weather WHERE listId LIKE :id")
    List<Weather> getWeather(String id);

    @Query("SELECT * FROM temperature WHERE listId LIKE :id")
    Temperature getTemperature(String id);

}
