package com.fesskiev.programmingsamples.threads.deadlock;


import android.util.Log;

public class Deadlock {

    public static class Friend {

        private final String name;

        public Friend(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public synchronized void bow(Friend bower) {
            Log.wtf(Deadlock.class.getSimpleName(), String.format("%s: %s has bowed to me!%n", this.name, bower.getName()));
            bower.bowBack(this);
        }

        public synchronized void bowBack(Friend bower) {
            Log.wtf(Deadlock.class.getSimpleName(), String.format("%s: %s has bowed back to me!%n", this.name, bower.getName()));
        }
    }

    public static void createDeadlock() {
        final Friend alphonse = new Friend("Alphonse");
        final Friend gaston = new Friend("Gaston");

        new Thread(() -> alphonse.bow(gaston), "Thread 1").start();
        new Thread(() -> gaston.bow(alphonse), "Thread 1").start();
    }
}
