package com.fesskiev.programmingsamples.threads.race;


import android.util.Log;

public class RaceConditions {

    public static class Counter {

        private int count;

        public void increment() {
            for (count = 0; count < 5; count++) {
                Log.wtf(RaceConditions.class.getSimpleName(), "count: " + count + " thread: " + Thread.currentThread().getName()
                + " state: " + Thread.currentThread().getState().name() + " priority: " + Thread.currentThread().getPriority());
            }
        }
    }

    public static void createRaceConditions() {
        Counter counter = new Counter();

        new Thread(counter::increment, "Thread 1").start();
        new Thread(counter::increment, "Thread 2").start();
    }
}
