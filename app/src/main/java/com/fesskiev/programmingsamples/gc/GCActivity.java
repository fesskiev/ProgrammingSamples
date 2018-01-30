package com.fesskiev.programmingsamples.gc;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.fesskiev.programmingsamples.R;

/**
 * GC explanation
 * http://javarevisited.blogspot.in/2011/04/garbage-collection-in-java.html
 * http://javarevisited.blogspot.sg/2014/03/difference-between-weakreference-vs-softreference-phantom-strong-reference-java.html
 * https://www.youtube.com/watch?v=iGRfyhE02lA
 */
public class GCActivity extends AppCompatActivity {

    private static final String TAG = GCActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gc);
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> generateOOM());
    }

    public void generateOOM() {
        int iteratorValue = 20;
        Log.wtf(TAG, "OOM test started");
        for (int i = 1; i < iteratorValue; i++) {
            Log.wtf(TAG, "Iteration " + i + " Free Mem: " + Runtime.getRuntime().freeMemory());
            int loop = 2;
            int[] memoryFillIntVar = new int[iteratorValue];
            // feel memoryFillIntVar array in loop..
            do {
                memoryFillIntVar[loop] = 0;
                loop--;
            } while (loop > 0);
            iteratorValue = iteratorValue * 5;
            Log.wtf(TAG, "Required Memory for next loop: " + iteratorValue);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
