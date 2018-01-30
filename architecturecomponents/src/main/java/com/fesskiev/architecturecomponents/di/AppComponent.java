package com.fesskiev.architecturecomponents.di;

import com.fesskiev.architecturecomponents.domain.source.location.LocationService;
import com.fesskiev.architecturecomponents.ui.cities.CitiesViewModel;
import com.fesskiev.architecturecomponents.ui.map.MapViewModel;
import com.fesskiev.architecturecomponents.ui.weather.WeatherViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {
        AppModule.class,
        RxBusModule.class,

        RepositoryModule.class,
        DataSourceModule.class,
        NetworkModule.class,
        DatabaseModule.class})

@Singleton
public interface AppComponent {
    void inject(CitiesViewModel viewModel);
    void inject(WeatherViewModel viewModel);
    void inject(MapViewModel viewModel);
    void inject(LocationService service);
}
