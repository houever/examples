package com.bingfa.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * synchronized 修饰代码块
 * synchronized 修饰方法
 * synchronized 修饰静态方法
 * synchronized 修饰类
 * synchronized 不可中断的锁，适合竞争不激烈的场景，可读性好
 * Lock 可中断锁，多样化同步，竞争激烈时能维持常态
 * Atomic，竞争激烈时能维持常态，比Lock性能好，；只能同步一个值
 */
@Slf4j
public class SyncTest {

    public static void main(String[] args) {
        SyncTest test = new SyncTest();
        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService executorService2 = Executors.newCachedThreadPool();

        executorService.execute(()->{
            test.test2(1);
        });
        executorService2.execute(()->{
            test.test2(2);
        });
        executorService.shutdown();
        executorService2.shutdown();
    }





    //修饰一个代码块
    public void test1(int j){
        synchronized (this){
            for(int i=0;i<10;i++){
                log.debug("j=={}---i=={}",j,i);
            }
        }
    }
    //修饰方法
    //方法被称为同步方法
    public void test2(int j){
        for(int i=0;i<10;i++){
            log.debug("j=={}---i=={}",j,i);
        }
    }

}
