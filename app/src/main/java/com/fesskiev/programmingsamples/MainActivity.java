package com.fesskiev.programmingsamples;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.fesskiev.programmingsamples.algorithm.AlgorithmActivity;
import com.fesskiev.programmingsamples.android.AndroidSystemActivity;
import com.fesskiev.programmingsamples.android.view.ViewsActivity;
import com.fesskiev.programmingsamples.gc.GCActivity;
import com.fesskiev.programmingsamples.rx.RxActivity;
import com.fesskiev.programmingsamples.threads.ThreadsActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setupButtons();
    }

    private void setupButtons() {
        findViewById(R.id.gcButton).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, GCActivity.class)));
        findViewById(R.id.androidButton).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AndroidSystemActivity.class)));
        findViewById(R.id.threadsButton).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ThreadsActivity.class)));
        findViewById(R.id.algorithmButton).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AlgorithmActivity.class)));
        findViewById(R.id.viewsButton).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ViewsActivity.class)));
        findViewById(R.id.rxJavaButton).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RxActivity.class)));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
