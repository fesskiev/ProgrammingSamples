package com.fesskiev.architecturecomponents.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.fesskiev.architecturecomponents.domain.source.local.room.WeatherDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    private String databaseName;

    public DatabaseModule(String databaseName) {
        this.databaseName = databaseName;
    }

    @Provides
    @Singleton
    public WeatherDatabase provideRoomDatabase(Context context) {
        return Room.databaseBuilder(context, WeatherDatabase.class, databaseName).build();
    }

}
