package com.fesskiev.programmingsamples.android.lifecycle;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.fesskiev.programmingsamples.R;

public class LifecycleActivity extends AppCompatActivity {

    private static final String TAG = LifecycleActivity.class.getSimpleName();

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.wtf(TAG, "onCreate");
        handler = new Handler();

        setContentView(R.layout.activity_lifecycle);
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> addFragmentsToBackStack());
    }

    private void addFragmentsToBackStack() {
        addFirstFragment();
//        handler.postDelayed(this::addSecondFragment, 3000);
    }

    private void addFirstFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.addToBackStack(LifecycleFragment1.class.getName());
        transaction.add(R.id.fragment_container, LifecycleFragment1.newInstance(), LifecycleFragment1.class.getName());
        transaction.commit();
    }

    private void addSecondFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.addToBackStack(LifecycleFragment2.class.getName());
        transaction.add(R.id.fragment_container, LifecycleFragment2.newInstance(), LifecycleFragment2.class.getName());
        transaction.commit();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.wtf(TAG, "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.wtf(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.wtf(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.wtf(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.wtf(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.wtf(TAG, "onDestroy");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.wtf(TAG, "onBackPressed");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.wtf(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.wtf(TAG, "onRestoreInstanceState");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.wtf(TAG, "onConfigurationChanged");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
