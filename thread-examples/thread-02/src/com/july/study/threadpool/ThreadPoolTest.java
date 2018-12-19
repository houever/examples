package com.july.study.threadpool;

import java.util.concurrent.Executors;

/**
 * corePoolSize： 核心池的大小。 当有任务来之后，就会创建一个线程去执行任务，当线程池中的线程数目达到corePoolSize后，就会把到达的任务放到缓存队列当中
 * maximumPoolSize： 线程池最大线程数，它表示在线程池中最多能创建多少个线程；
 * keepAliveTime： 表示线程没有任务执行时最多保持多久时间会终止。
 * unit： 参数keepAliveTime的时间单位，有7种取值，在TimeUnit类中有7种静态属性
 * ---------------------
 * 作者：kusedexingfu
 * 来源：CSDN
 * 原文：https://blog.csdn.net/kusedexingfu/article/details/72491864
 * 版权声明：本文为博主原创文章，转载请附上博文链接！
 */
public class ThreadPoolTest {
    public static void main(String[] args) {


        /***
         * TimeUnit.DAYS;               //天
         * TimeUnit.HOURS;             //小时
         * TimeUnit.MINUTES;           //分钟
         * TimeUnit.SECONDS;           //秒
         * TimeUnit.MILLISECONDS;      //毫秒
         * TimeUnit.MICROSECONDS;      //微妙
         * TimeUnit.NANOSECONDS;       //纳秒
         *
         * workQueue： 一个阻塞队列，用来存储等待执行的任务。 一般来说，这里的阻塞队列有以下几种选择：
         * ArrayBlockingQueue;
         * LinkedBlockingQueue;
         * SynchronousQueue
         *
         * threadFactory： 线程工厂，主要用来创建线程；
         * handler： 表示当拒绝处理任务时的策略，有以下四种取值：
         * ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
         * ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
         * ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
         * ThreadPoolExecutor.CallerRunsPolicy：只要线程池不关闭，该策略直接在调用者线程中，运行当前被丢弃的任务
         * 个人认为这4中策略不友好，最好自己定义拒绝策略，实现RejectedExecutionHandler接口
         */

        /**
         * 没有初始化，线程池最大数量为 2147483647（Integer.MAX_VALUE）
         *
         * public static ExecutorService newCachedThreadPool() {
         *         return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
         *                                       60L, TimeUnit.SECONDS,
         *                                       new SynchronousQueue<Runnable>());
         * 高并发时使用SynchronousQueue容易创建多个线程，导致线程池不断的扩大，不断创建新的线程
         *
         */
        Executors.newCachedThreadPool();


        /**
         * 初始化为10，最大线程数量10,
         * LinkedBlockingQueue 没有设置队列的上限
         *
         * public static ExecutorService newFixedThreadPool(int nThreads) {
         *         return new ThreadPoolExecutor(nThreads, nThreads,
         *                                       0L, TimeUnit.MILLISECONDS,
         *                                       new LinkedBlockingQueue<Runnable>());
         *  执行海量任务时会有任务堆积，内存会撑爆
         */
        Executors.newFixedThreadPool(3);
    }
}
