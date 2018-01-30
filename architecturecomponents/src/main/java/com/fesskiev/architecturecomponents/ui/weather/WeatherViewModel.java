package com.fesskiev.architecturecomponents.ui.weather;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.fesskiev.architecturecomponents.WeatherApplication;
import com.fesskiev.architecturecomponents.domain.entity.City;
import com.fesskiev.architecturecomponents.domain.entity.WeatherDesc;
import com.fesskiev.architecturecomponents.domain.source.DataRepository;
import com.fesskiev.architecturecomponents.ui.BaseViewModel;
import com.fesskiev.architecturecomponents.utils.Utils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class WeatherViewModel extends BaseViewModel {

    private final MutableLiveData<List<WeatherDesc>> weatherLiveData = new MutableLiveData<>();

    @Inject
    DataRepository dataRepository;

    @Inject
    Utils utils;

    private Disposable disposable;

    public WeatherViewModel() {
        WeatherApplication.getInstance().getAppComponent().inject(this);
    }

    public void fetchWeather(City city) {
        disposable = dataRepository.getWeather(city.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::observeWeather, this::showLoadingError);
    }


    private void observeWeather(List<WeatherDesc> weatherLists) {
        weatherLiveData.setValue(weatherLists);
    }

    public LiveData<List<WeatherDesc>> getWeather() {
        return weatherLiveData;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

}
