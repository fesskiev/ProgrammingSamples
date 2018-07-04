package com.fesskiev.programmingsamples.threads.patterns;

public class ConsumerRunnable implements Runnable {

    private final Data data;
    private int consumeCount;

    ConsumerRunnable(Data data) {
        this.data = data;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (data) {
                if (data.getData() > 0) {
                    data.setData(data.getData() - 1);
                    System.out.println("consumer take data: " + data);
                } else {
                    System.out.println("consumer took all data, notify! " + data);
                    data.notifyAll();
                    consumeCount++;
                    if (consumeCount == 5) {
                        System.out.println("STOP!");
                        data.setProduce(false);
                        break;
                    }
                    try {
                        data.wait();
                        System.out.println("CONSUMER WAIT!...");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}