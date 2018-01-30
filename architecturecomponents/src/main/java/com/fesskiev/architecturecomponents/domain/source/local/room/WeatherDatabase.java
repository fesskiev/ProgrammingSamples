package com.fesskiev.architecturecomponents.domain.source.local.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.fesskiev.architecturecomponents.domain.entity.City;
import com.fesskiev.architecturecomponents.domain.entity.Coordinate;
import com.fesskiev.architecturecomponents.domain.entity.Temperature;
import com.fesskiev.architecturecomponents.domain.entity.Weather;
import com.fesskiev.architecturecomponents.domain.entity.WeatherDesc;
import com.fesskiev.architecturecomponents.domain.entity.WeatherResponse;


@Database(entities = {WeatherResponse.class, City.class, Coordinate.class,
        Temperature.class, Weather.class, WeatherDesc.class }, version = 2)
public abstract class WeatherDatabase extends RoomDatabase {

    public abstract WeatherDAO getWeatherDAO();

}