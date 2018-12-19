package com.bingfa.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * synchronized 修饰代码块
 * synchronized 修饰方法
 * synchronized 修饰静态方法
 * synchronized 修饰类
 */
@Slf4j
public class SyncTest2 {


    HashMap map = (HashMap) Collections.synchronizedMap(new HashMap<String,String>());


    public static void main(String[] args) {
        SyncTest2 test = new SyncTest2();
        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService executorService2 = Executors.newCachedThreadPool();

        executorService.execute(()->{
            test.test1(1);
        });
        executorService2.execute(()->{
            test.test1(2);
        });
        executorService.shutdown();
        executorService2.shutdown();
    }


    /**
     * 修饰类，作用于所有对象
     * @param j
     */
    public void test1(int j){
        synchronized (SyncTest2.class){
            for(int i=0;i<10;i++){
                log.debug("j=={}---i=={}",j,i);
            }
        }
    }

    /**
     * 修饰静态方法，作用于所有对象
     * @param j
     */
    public static synchronized void test2(int j){
        for(int i=0;i<10;i++){
            log.debug("j=={}---i=={}",j,i);
        }
    }

}
