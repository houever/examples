package com.bingfa.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class SemaphoreTest {



    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        /**
         * 1秒内允许多少个线程执行
         */
        final Semaphore semaphore = new Semaphore(20);

        final CountDownLatch countDownLatch = new CountDownLatch(100);


        for(int i=0;i<=100;i++){
            final int threadNum = i;
            executorService.execute(()->{
                try {
//                    semaphore.acquire();  获取许可
//                    System.out.println("");
//                    semaphore.release();  释放许可

//                    semaphore.acquire(3);  //获取许可
//                    System.out.println(threadNum);
//                    semaphore.release(3);  //释放许可

                    semaphore.tryAcquire(3);  //获取许可
                    boolean b = semaphore.tryAcquire(1000, TimeUnit.MICROSECONDS);//单位时间内获取锁
                    if(b){
                        System.out.println(threadNum);
                        semaphore.release(3);  //释放许可
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

    }
}
