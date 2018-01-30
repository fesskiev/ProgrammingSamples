package com.fesskiev.programmingsamples.threads.basic;


public class Waiter implements Runnable {

    private Message message;

    public Waiter(Message m) {
        this.message = m;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        synchronized (message) {
            try {
                System.out.println(name + " waiting to get notified at time:" + System.currentTimeMillis());
                message.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + " waiter thread got notified at time:" + System.currentTimeMillis());
            System.out.println(name + " processed: " + message.getMessage());
        }
    }
}