
package com.fesskiev.architecturecomponents.domain.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "city")
public class City extends DBEntity implements Parcelable {

    @SerializedName("name")
    private String name;

    @Ignore
    @SerializedName("coord")
    private Coordinate coordinate;

    @SerializedName("country")
    private String country;

    @SerializedName("population")
    private int population;

    public City() {

    }

    public City(String name, String country, int population) {
        this.name = name;
        this.country = country;
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    @Override
    public String toString() {
        return "City{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", coordinate=" + coordinate +
                ", country='" + country + '\'' +
                ", population=" + population +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeParcelable(this.coordinate, flags);
        dest.writeString(this.country);
        dest.writeInt(this.population);
        dest.writeString(this.id);
    }

    protected City(Parcel in) {
        this.name = in.readString();
        this.coordinate = in.readParcelable(Coordinate.class.getClassLoader());
        this.country = in.readString();
        this.population = in.readInt();
        this.id = in.readString();
    }

    public static final Parcelable.Creator<City> CREATOR = new Parcelable.Creator<City>() {
        @Override
        public City createFromParcel(Parcel source) {
            return new City(source);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };
}
