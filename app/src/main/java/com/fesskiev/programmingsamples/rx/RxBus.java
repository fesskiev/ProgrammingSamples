package com.fesskiev.programmingsamples.rx;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RxBus {

    private static RxBus INSTANCE;

    public static RxBus getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RxBus();
        }
        return INSTANCE;
    }

    private final PublishSubject<Event> bus = PublishSubject.create();

    private RxBus() {

    }

    public void send(final Event event) {
        if (bus.hasObservers()) {
            bus.onNext(event);
        }
    }

    public Observable<Event> toObservable() {
        return bus;
    }
}
