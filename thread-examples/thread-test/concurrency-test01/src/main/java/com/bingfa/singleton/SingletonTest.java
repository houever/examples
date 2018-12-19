package com.bingfa.singleton;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Slf4j
public class SingletonTest {

    public static void main(String[] args) {

        CountDownLatch count = new CountDownLatch(1000);

        ExecutorService executorService = Executors.newCachedThreadPool();

        for(int i=0;i<=100;i++){
            executorService.execute(()->{
                SingletonTest4 test4 = SingletonTest4.getSingletonTest1();
                log.debug("test3=={}",test4);
            });
            count.countDown();
        }
        executorService.shutdown();
    }

}
