package com.fesskiev.programmingsamples.android;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.fesskiev.programmingsamples.R;
import com.fesskiev.programmingsamples.android.lifecycle.LifecycleActivity;
import com.fesskiev.programmingsamples.android.services.LongRunningIntentService;
import com.fesskiev.programmingsamples.android.services.MessageService;
import com.fesskiev.programmingsamples.android.tasks.SingleInstanceActivity;
import com.fesskiev.programmingsamples.android.tasks.SingleTaskActivity;
import com.fesskiev.programmingsamples.android.tasks.SingleTopActivity;

public class AndroidSystemActivity extends AppCompatActivity {

    private ServiceConnection serviceConnection;
    private Messenger messenger;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android);
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        handler = new Handler();

        setupButtons();
        bindingService();
    }

    private void setupButtons() {
        findViewById(R.id.fab).setOnClickListener(view -> sendMessage());

        findViewById(R.id.singleTopButton).setOnClickListener(v -> handleSingleTop());
        findViewById(R.id.singleTaskButton).setOnClickListener(v -> handleSingleTask());
        findViewById(R.id.singleInstanceButton).setOnClickListener(v -> handleSingleInstance());

        findViewById(R.id.lifecycleButton).setOnClickListener(v -> handleLifecycle());
    }

    private void handleLifecycle() {
        startActivity(new Intent(AndroidSystemActivity.this, LifecycleActivity.class));
    }

    private void handleSingleTop() {
        startActivity(new Intent(AndroidSystemActivity.this, SingleTopActivity.class));
        handler.postDelayed(() -> startActivity(new Intent(AndroidSystemActivity.this, SingleTopActivity.class)), 2000);
    }

    private void handleSingleTask() {
        startActivity(new Intent(AndroidSystemActivity.this, SingleTaskActivity.class));
        handler.postDelayed(() -> startActivity(new Intent(AndroidSystemActivity.this, SingleTaskActivity.class)), 2000);
    }

    private void handleSingleInstance() {
        startActivity(new Intent(AndroidSystemActivity.this, SingleInstanceActivity.class));
        handler.postDelayed(() -> startActivity(new Intent(AndroidSystemActivity.this, SingleInstanceActivity.class)), 2000);
    }

    private void sendMessage() {
        try {
            Message message = new Message();
            message.what = MessageService.MESSAGE;
            messenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void bindingService() {
        serviceConnection= new ServiceConnection() {

            @Override
            public void onServiceDisconnected(ComponentName name) {
                messenger = null;
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                messenger = new Messenger(service);
            }
        };
        bindService(new Intent(this, MessageService.class), serviceConnection,
                Context.BIND_AUTO_CREATE);
    }

    private void unbindingService() {
        unbindService(serviceConnection);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindingService();
    }

    private void startSeveralIntentServices() {
        for (int i = 0; i < 5; i++) {
            LongRunningIntentService.startServiceFakeHTTPRequest(getApplicationContext());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
