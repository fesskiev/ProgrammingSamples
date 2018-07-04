package com.fesskiev.programmingsamples.android.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class MessageService extends Service {

    private static final String TAG = MessageService.class.getSimpleName();
    public static final int MESSAGE = 0;

    private Messenger messenger = new Messenger(new MessageHandler());

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    class MessageHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            int msgType = msg.what;
            switch (msgType) {
                case MESSAGE: {
                    Log.wtf(TAG, "MESSAGE");
                    break;
                }
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
