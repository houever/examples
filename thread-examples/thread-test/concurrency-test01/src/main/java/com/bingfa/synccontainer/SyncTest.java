package com.bingfa.synccontainer;

import lombok.extern.slf4j.Slf4j;

import javax.xml.ws.soap.Addressing;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
public class SyncTest {

    private static int clientTotal = 5000;

    private static int threadTotal = 2000;

    private static List<Integer> list = Collections.synchronizedList(new ArrayList<Integer>());

    public static void main(String[] args) {


        ExecutorService executorService = Executors.newCachedThreadPool();

        CountDownLatch countDownLatch = new CountDownLatch(threadTotal);

        //信号量，允许并发的数量
        final Semaphore semaphore = new Semaphore(threadTotal);

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

        log.info("{}",list.size());
        countDownLatch.countDown();
        executorService.shutdown();

    }

    public static void add(){
        list.add(1);
    }
}
