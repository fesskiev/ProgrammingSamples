package com.fesskiev.architecturecomponents.ui.map;

import android.arch.lifecycle.MutableLiveData;
import android.location.Location;

import com.fesskiev.architecturecomponents.WeatherApplication;
import com.fesskiev.architecturecomponents.domain.entity.WeatherResponse;
import com.fesskiev.architecturecomponents.domain.source.DataRepository;
import com.fesskiev.architecturecomponents.ui.BaseViewModel;
import com.fesskiev.architecturecomponents.utils.RxBus;
import com.fesskiev.architecturecomponents.utils.Utils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MapViewModel extends BaseViewModel {

    private final MutableLiveData<WeatherResponse> weatherLiveData = new MutableLiveData<>();

    private Disposable disposable;

    @Inject
    DataRepository dataRepository;

    @Inject
    RxBus rxBus;

    @Inject
    Utils utils;

    public MapViewModel() {
        WeatherApplication.getInstance().getAppComponent().inject(this);
    }

    public void subscribeToLocationUpdates() {
        disposable = rxBus.toObservable()
                .observeOn(Schedulers.io())
                .flatMap(object -> {
                    if (object instanceof Location) {
                        Location location = (Location) object;

                        boolean isNetworkAvailable = utils.isNetworkAvailable();
                        if (!isNetworkAvailable) {
                            showNetworkNotAvailableError();
                        }
                        dataRepository.setNetworkAvailable(isNetworkAvailable);
                        return dataRepository.getWeatherByLocation(location.getLatitude(), location.getLongitude());
                    }
                    return Observable.empty();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::observeWeather, this::showLoadingError);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    private void observeWeather(WeatherResponse weatherResponse) {
        weatherLiveData.setValue(weatherResponse);
    }

    public MutableLiveData<WeatherResponse> getWeatherLiveData() {
        return weatherLiveData;
    }


}
