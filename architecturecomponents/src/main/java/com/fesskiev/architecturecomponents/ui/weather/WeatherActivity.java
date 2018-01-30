package com.fesskiev.architecturecomponents.ui.weather;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.fesskiev.architecturecomponents.R;
import com.fesskiev.architecturecomponents.domain.entity.City;
import com.fesskiev.architecturecomponents.domain.entity.Temperature;
import com.fesskiev.architecturecomponents.domain.entity.Weather;
import com.fesskiev.architecturecomponents.domain.entity.WeatherDesc;
import com.fesskiev.architecturecomponents.ui.cities.CitiesActivity;
import com.fesskiev.architecturecomponents.utils.SnackbarMessage;
import com.fesskiev.architecturecomponents.utils.Utils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class WeatherActivity extends AppCompatActivity {

    private WeatherViewModel viewModel;
    private WeatherAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        viewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);

        City city = getIntent().getParcelableExtra(CitiesActivity.EXTRA_CITY);
        if (city != null) {
            getSupportActionBar().setTitle(city.getName());
            viewModel.fetchWeather(city);
        }
        setupRecyclerView();
        setupDataObserver();
        setupShackBarObserver();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new WeatherAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void setupDataObserver() {
        viewModel.getWeather().observe(this, weatherLists -> adapter.refreshAdapter(weatherLists));
    }

    private void setupShackBarObserver() {
        viewModel.getSnackbarMessage().observe(this, (SnackbarMessage.SnackbarObserver) this::showSnackBar);
    }

    private void showSnackBar(int resourceId) {
        Snackbar.make(findViewById(R.id.rootView), getString(resourceId), Snackbar.LENGTH_LONG).show();
    }

    private static class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

        private List<WeatherDesc> weatherLists;
        private WeakReference<Activity> activity;

        public WeatherAdapter(Activity activity) {
            this.activity = new WeakReference<>(activity);
            this.weatherLists = new ArrayList<>();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView date;
            TextView morningTemp;
            TextView dayTemp;
            TextView nightTemp;
            ImageView weatherIcon;
            TextView description;

            public ViewHolder(View v) {
                super(v);
                date = v.findViewById(R.id.date);
                morningTemp = v.findViewById(R.id.morningTemp);
                dayTemp = v.findViewById(R.id.dayTemp);
                nightTemp = v.findViewById(R.id.nightTemp);
                weatherIcon = v.findViewById(R.id.weatherIcon);
                description = v.findViewById(R.id.description);
            }
        }

        @Override
        public WeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_weather, parent, false);
            return new WeatherAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(WeatherAdapter.ViewHolder holder, int position) {
            WeatherDesc weatherList = weatherLists.get(position);
            if (weatherList != null) {

                holder.date.setText(Utils.getDateFromUnixTime(weatherList.getDate()));

                Temperature temperature = weatherList.getTemp();
                holder.morningTemp.setText(String.format("%1$s %2$s", "morning:", String.valueOf(temperature.getMorning())));
                holder.dayTemp.setText(String.format("%1$s %2$s", "day:", String.valueOf(temperature.getDay())));
                holder.nightTemp.setText(String.format("%1$s %2$s", "night:", String.valueOf(temperature.getNight())));

                Weather weather = weatherList.getWeather().get(0);
                holder.description.setText(String.format("%1$s: %2$s", weather.getMain(), weather.getDescription()));
                Activity act = activity.get();
                if (act != null) {
                    Glide.with(act)
                            .load("http://openweathermap.org/img/w/" + weather.getIcon() + ".png")
                            .into(holder.weatherIcon);
                }
            }
        }

        @Override
        public int getItemCount() {
            return weatherLists.size();
        }

        public void refreshAdapter(List<WeatherDesc> weatherLists) {
            this.weatherLists.clear();
            this.weatherLists.addAll(weatherLists);
            notifyDataSetChanged();
        }
    }

}
