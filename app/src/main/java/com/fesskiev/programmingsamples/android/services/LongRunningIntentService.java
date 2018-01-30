package com.fesskiev.programmingsamples.android.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class LongRunningIntentService extends IntentService {

    private static final String TAG = "com.fesskiev.programmingsamples.action.ACTION_HTTP_REQUEST";

    private static final String ACTION_HTTP_REQUEST = "com.fesskiev.programmingsamples.action.ACTION_HTTP_REQUEST";

    public LongRunningIntentService() {
        super(LongRunningIntentService.class.getSimpleName());
    }

    public static void startServiceFakeHTTPRequest(Context context) {
        Intent intent = new Intent(context, LongRunningIntentService.class);
        intent.setAction(ACTION_HTTP_REQUEST);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.wtf(TAG, "Service onCreate()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.wtf(TAG, "Service onDestroy()");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.wtf(TAG, "Service onHandleIntent");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_HTTP_REQUEST.equals(action)) {
                fakeHttpRequest();
            }
        }
    }

    private void fakeHttpRequest() {
        Log.wtf(TAG, "Service started fetch fakeHttpRequest: " + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.wtf(TAG, "Service end fetch fakeHttpRequest: " + Thread.currentThread().getName());
    }

}
