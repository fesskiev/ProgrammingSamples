
package com.fesskiev.architecturecomponents.domain.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "weather_list",
        foreignKeys = @ForeignKey(
                entity = City.class, parentColumns = "id",
                childColumns = "cityId",
                onDelete = CASCADE),
        indices = @Index("cityId"))
public class WeatherDesc extends DBEntity {

    private String cityId;

    @SerializedName("dt")
    private int date;

    @Ignore
    @SerializedName("temp")
    private Temperature temp;

    @SerializedName("pressure")
    private double pressure;

    @SerializedName("humidity")
    private int humidity;

    @Ignore
    @SerializedName("weather")
    private List<Weather> weather = new ArrayList<>();

    @SerializedName("speed")
    private double speed;

    @SerializedName("deg")
    private int deg;

    @SerializedName("clouds")
    private int clouds;

    @SerializedName("rain")
    private double rain;

    public WeatherDesc() {

    }

    public WeatherDesc(int date, double pressure, int humidity,
                       double speed, int deg, int clouds, double rain) {
        this.date = date;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.speed = speed;
        this.deg = deg;
        this.clouds = clouds;
        this.rain = rain;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public Temperature getTemp() {
        return temp;
    }

    public void setTemp(Temperature temp) {
        this.temp = temp;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    @Override
    public String toString() {
        return "WeatherList{" +
                "id='" + id + '\'' +
                ", cityId='" + cityId + '\'' +
                ", date=" + date +
                ", temp=" + temp +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", weather=" + weather +
                ", speed=" + speed +
                ", deg=" + deg +
                ", clouds=" + clouds +
                ", rain=" + rain +
                '}';
    }
}
