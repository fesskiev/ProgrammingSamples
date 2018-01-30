package com.fesskiev.architecturecomponents.ui.cities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.fesskiev.architecturecomponents.WeatherApplication;
import com.fesskiev.architecturecomponents.domain.entity.City;
import com.fesskiev.architecturecomponents.domain.source.DataRepository;
import com.fesskiev.architecturecomponents.ui.BaseViewModel;
import com.fesskiev.architecturecomponents.utils.Utils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class CitiesViewModel extends BaseViewModel {

    private final MutableLiveData<List<City>> weatherResponseLiveData = new MutableLiveData<>();

    @Inject
    DataRepository dataRepository;

    @Inject
    Utils utils;

    private Disposable disposable;

    public CitiesViewModel() {
        WeatherApplication.getInstance().getAppComponent().inject(this);
        getCities();
    }

    private void getCities() {
        disposable = dataRepository.getCities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::observeCities, this::showLoadingError);
    }

    public void fetchWeather(String city) {
        boolean isNetworkAvailable = utils.isNetworkAvailable();
        if (!isNetworkAvailable) {
            showNetworkNotAvailableError();
        }
        dataRepository.setNetworkAvailable(isNetworkAvailable);
        disposable = dataRepository.getWeatherByCity(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weatherResponse -> getCities(), this::showLoadingError);
    }

    public void deleteCity(City city) {
        disposable = dataRepository.deleteCity(city)
                .subscribeOn(Schedulers.io())
                .flatMap(aVoid -> dataRepository.getCities())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::observeCities);
    }

    private void observeCities(List<City> cities) {
        weatherResponseLiveData.setValue(cities);
    }

    public LiveData<List<City>> getWeatherData() {
        return weatherResponseLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
