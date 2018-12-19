package com.bingfa.test;

import com.bingfa.annotations.NotThreadSafe;
import com.bingfa.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Atomic 同一时刻只能同一个线程访问，实现同步
 */
//AtomicInteger线程安全
@Slf4j
@ThreadSafe
public class ConcurrencyTest2 {

    public static int clientTotal = 5000;

    public static int threadTotal = 200;

    public static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();

        //信号量，允许并发的数量
        final Semaphore semaphore = new Semaphore(threadTotal);

        //模拟有1000个并发
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

        for(int i=0; i<clientTotal; i++){
            executorService.execute(()->{
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    log.info("{}",e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("count={}",count);
    }

    /**
     * 拿当前对象的值和底层的值进行对比，如果一样，执行+的操作
     */
    private static void add(){
        count.incrementAndGet();
    }

}
