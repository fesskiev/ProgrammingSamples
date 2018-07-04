package com.fesskiev.programmingsamples.threads.patterns;

public class ProducerConsumer {

    private static final Data data = new Data();

    public static void main(String[] args) {

        new Thread(new ProducerRunnable(data)).start();
        new Thread(new ConsumerRunnable(data)).start();
    }
}