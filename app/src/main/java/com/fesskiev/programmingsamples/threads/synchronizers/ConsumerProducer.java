package com.fesskiev.programmingsamples.threads.synchronizers;

import java.util.UUID;

public class ConsumerProducer {

    private static final Data<String> data = new Data<>();

    private static volatile boolean active = true;

    public static void main(String[] args) {

        new Thread(new DelayRunnable()).start();
        new Thread(new ConsumerRunnable()).start();
        new Thread(new ProducerRunnable()).start();
    }

    private static class ConsumerRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println("*start consumer*");
            while (true) {
                synchronized (data) {
                    if (!active) {
                        System.out.println("*start consumer not active*");
                        data.notifyAll();
                        return;
                    }
                    if (!data.isEmpty()) {
                        String value = data.getData();
                        System.out.println("consumer get data: " + value);

                        data.notifyAll();
                    }
                    try {
                        data.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static class ProducerRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println("*start producer*");
            while (true) {
                synchronized (data) {
                    if (!active) {
                        System.out.println("*start producer not active*");
                        data.notifyAll();
                        return;
                    }
                    if (data.isEmpty()) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        data.addData(UUID.randomUUID().toString());
                        System.out.println("producer add data!");

                        data.notifyAll();
                    }
                    try {
                        data.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static class DelayRunnable implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(14000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            active = false;
            System.out.println("Shutdown!");
        }
    }
}
