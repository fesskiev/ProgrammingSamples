package com.fesskiev.programmingsamples.algorithm;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.fesskiev.programmingsamples.R;

import java.util.Arrays;

public class AlgorithmActivity extends AppCompatActivity {

    private int[] sortArray = new int[]{2, 16, 7, 11, 0, 5, 34, 1, 90, 62};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithm);
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> sortArray(sortArray));
    }

    private static void sortArray(int[] array) {
        boolean needSort = true;
        while (needSort) {
            needSort = false;
            for (int i = 0; i < array.length - 1; i++) {
                int current = array[i];
                int next = array[i + 1];
                if (current > next) {
                    needSort = true;
                    swap(array, i, current, next);
                }
            }
        }
        Log.wtf("test", "sorting: " + Arrays.toString(array));
    }

    private static void swap(int[] array, int i, int current, int next) {
        int tmp = next;
        next = current;
        current = tmp;
        array[i] = current;
        array[i + 1] = next;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
