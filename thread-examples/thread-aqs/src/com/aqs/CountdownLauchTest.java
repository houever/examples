package com.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountdownLauchTest {

    private static int threadcount = 200;

    public static void main(String[] args) {

        ExecutorService exec = Executors.newCachedThreadPool();

        final CountDownLatch countDownLatch = new CountDownLatch(threadcount);

        for(int i=0;i<threadcount;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(System.currentTimeMillis());
                }
            }).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
