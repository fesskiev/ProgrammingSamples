package com.fesskiev.programmingsamples.threads.executors;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorsExample {

    public static void main(String[] args) {
        System.out.println("Inside : " + Thread.currentThread().getName());

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Runnable runnable = () -> {
            System.out.println("Inside : " + Thread.currentThread().getName());
        };

        executorService.submit(runnable);
    }
}
