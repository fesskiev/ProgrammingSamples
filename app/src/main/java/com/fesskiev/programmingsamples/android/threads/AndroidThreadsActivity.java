package com.fesskiev.programmingsamples.android.threads;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.fesskiev.programmingsamples.R;

public class AndroidThreadsActivity extends AppCompatActivity {

    private class LooperThread extends Thread {

        private Handler handler;

        @SuppressLint("HandlerLeak")
        public void run() {
            Looper.prepare();
            handler = new Handler() {
                public void handleMessage(Message msg) {
                    Log.wtf("test", "looper thread message: " + msg.what + " is main: "
                            + (Looper.getMainLooper() == Looper.myLooper()));
                    Log.wtf("test", "start to do blocking job");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.wtf("test", "notify result to main thread!");
                    uiHandler.sendEmptyMessage(12);
                }
            };
            Looper.loop();
        }

        public void exit() {
            Looper looper = Looper.myLooper();
            if (looper != null) {
                looper.quitSafely();
            }
        }
    }

    private LooperThread looperThread;
    private Handler uiHandler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.wtf("test", "main thread message: " + msg.what + " is main: "
                    + (Looper.getMainLooper() == Looper.myLooper()));
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_threads);

        looperThread = new LooperThread();
        looperThread.start();

        findViewById(R.id.startThreads).setOnClickListener(v -> looperThread.handler.sendEmptyMessage(11));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        looperThread.exit();
    }
}
