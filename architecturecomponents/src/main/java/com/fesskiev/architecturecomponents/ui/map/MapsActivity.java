package com.fesskiev.architecturecomponents.ui.map;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fesskiev.architecturecomponents.R;
import com.fesskiev.architecturecomponents.domain.entity.City;
import com.fesskiev.architecturecomponents.domain.entity.Coordinate;
import com.fesskiev.architecturecomponents.domain.entity.Temperature;
import com.fesskiev.architecturecomponents.domain.entity.Weather;
import com.fesskiev.architecturecomponents.domain.entity.WeatherDesc;
import com.fesskiev.architecturecomponents.domain.entity.WeatherResponse;
import com.fesskiev.architecturecomponents.domain.source.location.LocationService;
import com.fesskiev.architecturecomponents.utils.SnackbarMessage;
import com.fesskiev.architecturecomponents.utils.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final int PERMISSION_REQ = 0;

    private TextView date;
    private TextView morningTemp;
    private TextView dayTemp;
    private TextView nightTemp;
    private ImageView weatherIcon;
    private TextView description;

    private GoogleMap googleMap;
    private MapViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        viewModel = ViewModelProviders.of(this).get(MapViewModel.class);
        setupDataObserver();
        setupShackBarObserver();
        setupViews();

        if (!checkPermissions()) {
            requestPermissions();
        } else {
            startLocationService();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocationService.stopLocationService(getApplicationContext());
    }

    private void setupViews() {
        date = findViewById(R.id.date);
        morningTemp = findViewById(R.id.morningTemp);
        dayTemp = findViewById(R.id.dayTemp);
        nightTemp = findViewById(R.id.nightTemp);
        weatherIcon = findViewById(R.id.weatherIcon);
        description = findViewById(R.id.description);
    }

    private void setupDataObserver() {
        viewModel.getWeatherLiveData().observe(this, this::fillData);
    }

    private void fillData(WeatherResponse weatherResponse) {
        if (weatherResponse != null) {
            WeatherDesc weatherList = weatherResponse.getWeatherList().get(0);

            date.setText(Utils.getDateFromUnixTime(weatherList.getDate()));

            Temperature temperature = weatherList.getTemp();
            morningTemp.setText(String.format("%1$s %2$s", "morning:", String.valueOf(temperature.getMorning())));
            dayTemp.setText(String.format("%1$s %2$s", "day:", String.valueOf(temperature.getDay())));
            nightTemp.setText(String.format("%1$s %2$s", "night:", String.valueOf(temperature.getNight())));

            Weather weather = weatherList.getWeather().get(0);
            description.setText(String.format("%1$s: %2$s", weather.getMain(), weather.getDescription()));
            Glide.with(getApplicationContext())
                    .load("http://openweathermap.org/img/w/" + weather.getIcon() + ".png")
                    .into(weatherIcon);

            City city = weatherResponse.getCity();
            Coordinate coordinate = city.getCoordinate();

            if (googleMap != null) {
                LatLng sydney = new LatLng(coordinate.getLatitude(), coordinate.getLongitude());
                this.googleMap.addMarker(new MarkerOptions().position(sydney).title(city.getName()));
                this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }
        }
    }

    private void setupShackBarObserver() {
        viewModel.getSnackbarMessage().observe(this, (SnackbarMessage.SnackbarObserver) this::showSnackBar);
    }

    private void showSnackBar(int resourceId) {
        Snackbar.make(findViewById(R.id.rootView), getString(resourceId), Snackbar.LENGTH_LONG).show();
    }


    private boolean checkPermissions() {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQ);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQ: {
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        startLocationService();
                    } else {
                        boolean shouldProvideRationale =
                                ActivityCompat.shouldShowRequestPermissionRationale(this,
                                        Manifest.permission.ACCESS_FINE_LOCATION);
                        if (shouldProvideRationale) {
                            permissionsDenied();
                        }
                    }
                }
                break;
            }
        }
    }

    private void permissionsDenied() {
        Snackbar.make(findViewById(R.id.rootView), getString(R.string.snackbar_permission_title), Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.snackbar_permission_button, v -> requestPermissions())
                .show();
    }

    private void startLocationService() {
        viewModel.subscribeToLocationUpdates();
        LocationService.startLocationService(getApplicationContext());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }
}
