package com.fesskiev.programmingsamples.rx;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.fesskiev.programmingsamples.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class RxActivity extends AppCompatActivity {

    private final static String TAG = RxActivity.class.getSimpleName();

    private static final List<Event> EMPTY_LIST = new ArrayList<>();

    private CompositeDisposable disposables;
    private RxBus rxBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        disposables = new CompositeDisposable();
        rxBus = RxBus.getInstance();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> generateEvents());

        observeEvents();
        checkDifferentTypeOfObserver();
    }

    private void checkDifferentTypeOfObserver() {
        disposables.add(Observable.just(EMPTY_LIST)
                .subscribe(events -> Log.wtf(TAG, "OBSERVABLE ON NEXT"),
                        throwable -> Log.wtf(TAG, "OBSERVABLE ON ERROR"),
                        () -> Log.wtf(TAG, "OBSERVABLE ON COMPLETE")));

        Log.wtf(TAG, "*******************************************");

        disposables.add(Flowable.just(EMPTY_LIST)
                .subscribe(events -> Log.wtf(TAG, "FLOWABLE ON NEXT"),
                        throwable -> Log.wtf(TAG, "FLOWABLE ON ERROR"),
                        () -> Log.wtf(TAG, "FLOWABLE ON COMPLETE")));

        Log.wtf(TAG, "*******************************************");

        disposables.add(Maybe.just(EMPTY_LIST)
                .subscribe(events -> Log.wtf(TAG, "MAYBE ON NEXT"),
                        throwable -> Log.wtf(TAG, "MAYBE ON ERROR"),
                        () -> Log.wtf(TAG, "MAYBE ON COMPLETE")));

        Log.wtf(TAG, "*******************************************");

        disposables.add(Single.just(EMPTY_LIST)
                .subscribe(events -> Log.wtf(TAG, "SINGLE ON NEXT" ),
                        throwable -> Log.wtf(TAG, "SINGLE ON ERROR" )));
    }

    private void generateEvents() {
        disposables.add(Observable.interval(0, 2000, TimeUnit.MILLISECONDS)
                .takeUntil(interval -> interval == 10)
                .subscribe(interval -> rxBus.send(new Event(interval)), Throwable::printStackTrace));
    }

    private void observeEvents() {
        disposables.add(rxBus.toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::printEvent, Throwable::printStackTrace));
    }

    private void printEvent(Event event) {
        Log.wtf(TAG, "event: " + event.toString());
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (disposables != null && !disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
