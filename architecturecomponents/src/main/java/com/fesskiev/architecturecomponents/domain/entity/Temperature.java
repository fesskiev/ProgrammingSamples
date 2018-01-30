
package com.fesskiev.architecturecomponents.domain.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

import com.google.gson.annotations.SerializedName;

import static android.arch.persistence.room.ForeignKey.CASCADE;


@Entity(tableName = "temperature",
        foreignKeys = @ForeignKey(
                entity = WeatherDesc.class, parentColumns = "id",
                childColumns = "listId",
                onDelete = CASCADE),
        indices = @Index("listId"))
public class Temperature extends DBEntity {

    private String listId;

    @SerializedName("day")
    private double day;

    @SerializedName("min")
    private double min;

    @SerializedName("max")
    private double max;

    @SerializedName("night")
    private double night;

    @SerializedName("eve")
    private double evening;

    @SerializedName("morn")
    private double morning;

    public Temperature() {

    }

    public Temperature(double day, double min, double max, double night, double evening, double morning) {
        this.day = day;
        this.min = min;
        this.max = max;
        this.night = night;
        this.evening = evening;
        this.morning = morning;
    }

    public double getDay() {
        return day;
    }

    public void setDay(double day) {
        this.day = day;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getNight() {
        return night;
    }

    public void setNight(double night) {
        this.night = night;
    }

    public double getEvening() {
        return evening;
    }

    public void setEvening(double evening) {
        this.evening = evening;
    }

    public double getMorning() {
        return morning;
    }

    public void setMorning(double morning) {
        this.morning = morning;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "id='" + id + '\'' +
                ", listId='" + listId + '\'' +
                ", day=" + day +
                ", min=" + min +
                ", max=" + max +
                ", night=" + night +
                ", evening=" + evening +
                ", morning=" + morning +
                '}';
    }
}
