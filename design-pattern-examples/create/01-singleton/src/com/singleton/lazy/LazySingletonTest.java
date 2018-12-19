package com.singleton.lazy;

import sun.java2d.pipe.SpanIterator;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import static com.singleton.lazy.LazySingleton.*;

/**
 * 测试懒汉单例模式
 */
public class LazySingletonTest {

    //同时并发执行的线程数
    private final static int threadCount = 200;

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();

        //300个线程同时访问
        final Semaphore semaphore = new Semaphore(300);

        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        //开启200个线程
        for(int i=0;i<threadCount;i++){
            executorService.execute(()->{
                try {
                    semaphore.acquire();
                    print();
                    //让当前线程等待
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    countDownLatch.countDown();
                }
            });

        }
        countDownLatch.await();
        //关闭线程池
        executorService.shutdown();

    }
    public static void print(){
        System.out.println(System.currentTimeMillis()+""+LazySingleton.getInstance());
    }


}
