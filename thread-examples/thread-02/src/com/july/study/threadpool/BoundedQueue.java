package com.july.study.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 有界队列
 * 1.初始的poolSize < corePoolSize，提交的runnable任务，会直接做为new一个Thread的参数，立马执行 。
 * 2.当提交的任务数超过了corePoolSize，会将当前的runable提交到一个block queue中,。
 * 3.有界队列满了之后，如果poolSize < maximumPoolsize时，会尝试new 一个Thread的进行救急处理，立马执行对应的runnable任务。
 * 4.如果3中也无法处理了，就会走到第四步执行reject操作。
 *
 * 原文：https://blog.csdn.net/kusedexingfu/article/details/72491864
 */
public class BoundedQueue implements Runnable{


    public String name;

    public BoundedQueue(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(name);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(3);
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                1, //corePoolSize
                2,	//maximumPoolSize
                1L,
                TimeUnit.SECONDS,
                workQueue
        );
        threadPool.execute(new BoundedQueue("任务1"));
        threadPool.execute(new BoundedQueue("任务2"));
        threadPool.execute(new BoundedQueue("任务3"));
        threadPool.execute(new BoundedQueue("任务4"));
        threadPool.execute(new BoundedQueue("任务5"));
        threadPool.execute(new BoundedQueue("任务6"));
        threadPool.shutdown();

        /**
         * 有界队列
         * 1.初始的poolSize < corePoolSize，提交的runnable任务，会直接做为new一个Thread的参数，立马执行 。
         * 2.当提交的任务数超过了corePoolSize，会将当前的runable提交到一个block queue中,。
         * 3.有界队列满了之后，如果poolSize < maximumPoolsize时，会尝试new 一个Thread的进行救急处理，立马执行对应的runnable任务。
         * 4.如果3中也无法处理了，就会走到第四步执行reject操作。
         * 且线程是两个两个执行的。
         *
         *
         * 分析：线程池的corePoolSize为1，任务1提交后，线程开始执行，corePoolSize 数量用完，
         * 接着任务2、3、4提交，放到了有界队列中，此时有界队列也满了。继续提交任务5，
         * 由于当前运行的线程数poolSize < maximumPoolsize,线程池尝试new一个新的线程来执行任务5，
         * 所以任务5会接着执行。当继续提交任务6,时，poolSize达到了maximumPoolSize，有界队列也满了，所以线程池执行了拒绝操作。
         */

    }

}
