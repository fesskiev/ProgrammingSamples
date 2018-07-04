package com.fesskiev.programmingsamples.threads.executors;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ExecutorsExample {

    public static void main(String[] args) {
//        System.out.println("Inside : " + Thread.currentThread().getName());
//
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//
//        Runnable runnable = () -> System.out.println("Inside : " + Thread.currentThread().getName());
//
//        executorService.submit(runnable);

        int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
        ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(NUMBER_OF_CORES);

        System.out.println("**Start execution**");
        for (int i = 0; i < 2; i++) {
            scheduledPool.schedule(taskRunnable, 2, TimeUnit.SECONDS);
        }

        ScheduledFuture sf = scheduledPool.schedule(taskCallable, 2, TimeUnit.SECONDS);
        String value = null;
        try {
            value = (String) sf.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("Callable returned: " + value);

        System.out.println("**Finish execution**");
        scheduledPool.shutdown();
    }

    private static Callable<String> taskCallable = () -> "Hello World!";

    private static Runnable taskRunnable = () -> {
        System.out.println("start download image!");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("finish download image!");
    };
}
