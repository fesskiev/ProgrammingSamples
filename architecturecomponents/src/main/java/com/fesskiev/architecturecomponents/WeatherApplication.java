package com.fesskiev.architecturecomponents;

import android.app.Application;

import com.fesskiev.architecturecomponents.di.AppComponent;
import com.fesskiev.architecturecomponents.di.AppModule;
import com.fesskiev.architecturecomponents.di.DaggerAppComponent;
import com.fesskiev.architecturecomponents.di.DataSourceModule;
import com.fesskiev.architecturecomponents.di.DatabaseModule;
import com.fesskiev.architecturecomponents.di.NetworkModule;
import com.fesskiev.architecturecomponents.di.RepositoryModule;


public class WeatherApplication extends Application {

    private static WeatherApplication application;

    public static synchronized WeatherApplication getInstance() {
        return application;
    }

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        appComponent = buildComponent();
    }

    protected AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .repositoryModule(new RepositoryModule())
                .dataSourceModule(new DataSourceModule())
                .databaseModule(new DatabaseModule("weather_database"))
                .networkModule(new NetworkModule("https://api.openweathermap.org/data/2.5/forecast/daily/"))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
