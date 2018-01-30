package com.fesskiev.architecturecomponents.ui.cities;

import android.app.Activity;
import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.fesskiev.architecturecomponents.R;
import com.fesskiev.architecturecomponents.domain.entity.City;
import com.fesskiev.architecturecomponents.ui.map.MapsActivity;
import com.fesskiev.architecturecomponents.ui.weather.WeatherActivity;
import com.fesskiev.architecturecomponents.utils.SnackbarMessage;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class CitiesActivity extends AppCompatActivity {

    public static final String EXTRA_CITY = "com.architecturecomponents.EXTRA_CITY";

    private CitiesViewModel viewModel;
    private SearchView searchView;
    private CitiesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
        findViewById(R.id.backIcon).setOnClickListener(v -> onBackPressed());

        viewModel = ViewModelProviders.of(this).get(CitiesViewModel.class);

        setupDataObserver();
        setupShackBarObserver();
        setupSearchView();
        setupRecyclerView();
        setupMapFab();
    }

    private void setupMapFab() {
        findViewById(R.id.fabMap).setOnClickListener(v -> startMapActivity());
    }

    private void setupSearchView() {
        searchView = findViewById(R.id.searchView);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        searchView.setImeOptions(searchView.getImeOptions() | EditorInfo.IME_ACTION_SEARCH |
                EditorInfo.IME_FLAG_NO_EXTRACT_UI | EditorInfo.IME_FLAG_NO_FULLSCREEN);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                querySearch(query);
                hideKeyboard();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return true;
            }
        });
        searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> {

        });
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new CitiesAdapter(this);
        recyclerView.setAdapter(adapter);
    }


    private void querySearch(String query) {
        viewModel.fetchWeather(query);
    }

    private void setupDataObserver() {
        viewModel.getWeatherData().observe(this, this::fillData);
    }

    private void fillData(List<City> cities) {
        if (cities != null) {
            adapter.refreshAdapter(cities);
        }
    }

    private void setupShackBarObserver() {
        viewModel.getSnackbarMessage().observe(this, (SnackbarMessage.SnackbarObserver) this::showSnackBar);
    }

    private void showSnackBar(int resourceId) {
        Snackbar.make(findViewById(R.id.rootView), getString(resourceId), Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent.hasExtra(SearchManager.QUERY)) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            if (!TextUtils.isEmpty(query)) {
                searchView.setQuery(query, false);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void deleteCity(City city) {
        viewModel.deleteCity(city);
    }

    private static class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {

        private List<City> cities;
        private WeakReference<Activity> activity;


        public CitiesAdapter(Activity activity) {
            this.activity = new WeakReference<>(activity);
            this.cities = new ArrayList<>();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView cityName;
            TextView cityCode;
            ImageView removeCity;

            public ViewHolder(View v) {
                super(v);
                v.setOnClickListener(v1 -> startWeatherDetailsActivity(getAdapterPosition()));
                cityName = v.findViewById(R.id.cityName);
                cityCode = v.findViewById(R.id.cityCode);
                removeCity = v.findViewById(R.id.removeCity);
                removeCity.setOnClickListener(v1 -> deleteCity(getAdapterPosition()));
            }
        }

        private void deleteCity(int position) {
            City city = cities.get(position);
            if (city != null) {
                Activity act = activity.get();
                if (act != null) {
                    ((CitiesActivity) act).deleteCity(city);
                }
            }
        }

        private void startWeatherDetailsActivity(int position) {
            City city = cities.get(position);
            if (city != null) {
                Activity act = activity.get();
                if (act != null) {
                    Intent intent = new Intent(act, WeatherActivity.class);
                    intent.putExtra(EXTRA_CITY, city);
                    act.startActivity(intent);
                }
            }
        }

        @Override
        public CitiesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_city, parent, false);
            return new CitiesAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(CitiesAdapter.ViewHolder holder, int position) {
            City city = cities.get(position);
            if (city != null) {
                holder.cityName.setText(city.getName());
                holder.cityCode.setText(city.getId());
            }
        }

        @Override
        public int getItemCount() {
            return cities.size();
        }

        public void refreshAdapter(List<City> c) {
            cities.clear();
            cities.addAll(c);
            notifyDataSetChanged();
        }

    }

    private void startMapActivity() {
        startActivity(new Intent(this, MapsActivity.class));
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
