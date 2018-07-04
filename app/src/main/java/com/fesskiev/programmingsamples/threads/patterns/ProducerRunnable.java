package com.fesskiev.programmingsamples.threads.patterns;

public class ProducerRunnable implements Runnable {

    private final Data data;

    ProducerRunnable(Data data) {
        this.data = data;
    }

    @Override
    public void run() {
        synchronized (data) {
            while (data.isProduce()) {
                if (data.getData() < 5) {
                    data.setData(data.getData() + 1);
                    System.out.println("producer make data: " + data);
                } else {
                    data.notifyAll();
                    System.out.println("producer made all data, notify!");
                    try {
                        data.wait();
                        System.out.println("PRODUCER WAIT!...");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}