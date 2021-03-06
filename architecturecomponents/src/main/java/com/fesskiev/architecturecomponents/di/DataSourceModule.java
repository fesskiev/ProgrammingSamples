package com.fesskiev.architecturecomponents.di;


import android.content.Context;

import com.fesskiev.architecturecomponents.domain.source.local.LocalDataSource;
import com.fesskiev.architecturecomponents.domain.source.local.room.WeatherDatabase;
import com.fesskiev.architecturecomponents.domain.source.remote.RemoteDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class DataSourceModule {

    @Provides
    @Singleton
    public RemoteDataSource provideRemoteDataSource(Context context, Retrofit retrofit) {
        return new RemoteDataSource(context, retrofit);
    }

    @Provides
    @Singleton
    public LocalDataSource provideLocalDataSource(WeatherDatabase database) {
        return new LocalDataSource(database);
    }

}
