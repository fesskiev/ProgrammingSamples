package com.fesskiev.architecturecomponents.room;


import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.fesskiev.architecturecomponents.domain.entity.City;
import com.fesskiev.architecturecomponents.domain.entity.Coordinate;
import com.fesskiev.architecturecomponents.domain.entity.Temperature;
import com.fesskiev.architecturecomponents.domain.entity.Weather;
import com.fesskiev.architecturecomponents.domain.entity.WeatherDesc;
import com.fesskiev.architecturecomponents.domain.source.local.room.WeatherDAO;
import com.fesskiev.architecturecomponents.domain.source.local.room.WeatherDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertSame;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class RoomTest {

    WeatherDatabase database;
    WeatherDAO weatherDAO;

    @Before
    public void setUp() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getTargetContext(),
                WeatherDatabase.class).build();
        weatherDAO = database.getWeatherDAO();
    }

    @Test
    public void deleteCity() {
        assertEquals(0, weatherDAO.getCities().size());

        City city = new City("Kiev", "UA", 0);

        Coordinate coordinate = new Coordinate(40.34d, 41.26d);
        coordinate.setCityId(city.getId());

        city.setCoordinate(coordinate);

        assertNotNull(city.getId());
        assertNotNull(coordinate.getCityId());

        weatherDAO.insertCity(city);
        database.getWeatherDAO().insertCoordinate(coordinate);

        WeatherDesc weatherDesc = new WeatherDesc(0, 0d, 0, 0d, 0, 0, 0d);
        weatherDesc.setCityId(city.getId());
        weatherDAO.insertWeatherList(weatherDesc);

        Temperature temperature = new Temperature(0d, 0d, 0d, 0d, 0d, 0d);
        temperature.setListId(weatherDesc.getId());
        weatherDAO.insertTemperature(temperature);

        Weather weather = new Weather("Clouds", "Rain", "10n");
        weather.setListId(weatherDesc.getId());
        weatherDAO.insertWeather(weather);

        assertEquals(1, weatherDAO.getCities().size());
        assertNotNull(weatherDAO.getCoordinate(city.getId()));
        assertEquals(1, weatherDAO.getWeatherList(city.getId()).size());
        assertNotNull(weatherDAO.getTemperature(weatherDesc.getId()));
        assertEquals(1, weatherDAO.getWeather(weatherDesc.getId()).size());


        weatherDAO.delete(city);

        assertNull(weatherDAO.getCity(city.getId()));
        assertNull(weatherDAO.getCoordinate(city.getId()));
        assertEquals(0, weatherDAO.getWeatherList(city.getId()).size());
        assertNull(weatherDAO.getTemperature(weatherDesc.getId()));
        assertEquals(0, weatherDAO.getWeather(weatherDesc.getId()).size());

    }

    @After
    public void closeDb() {
        database.close();
    }
}
