package com.july.study.threadpool;

import java.util.concurrent.*;

/**
 * 建议使用的创建线程池的方式
 */
public class ThreadPoolExample1 {

    public static void main(String[] args) {


        System.out.println(Integer.MAX_VALUE);

        /**
         * 建议：使用ThreadPoolExecutor来创建线程池
         * public ThreadPoolExecutor(int corePoolSize,      核心线程数
         *                               int maximumPoolSize, 最大线程数
         *                               long keepAliveTime, 线程活跃时间
         *                               TimeUnit unit,         时间单位
         *                               BlockingQueue<Runnable> workQueue,线程队列
         *                               ThreadFactory threadFactory,   线程工厂
         *                               RejectedExecutionHandler handler)
         * param1：5
         * param2:核心线程数Runtime.getRuntime().availableProcessors() 是系统核心数
         * param3:活跃时间60秒
         * param4:ArrayBlockingQueue 有界队列
         * param5:ThreadFactory  可以对线程做个性化创建，最合理的创建线程的方式
         * param6:
         */
        ThreadPoolExecutor poo1 = new ThreadPoolExecutor(5,
                Runtime.getRuntime().availableProcessors() * 2,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(200),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        //对线程做个性化配置
                        Thread thread = new Thread(r);
                        //设置线程名称
                        thread.setName("order-thread");
                        //设置是否是守护线程
                        if (thread.isDaemon()) {
                            thread.setDaemon(false);
                        }
                        //设置线程优先级
                        if (Thread.NORM_PRIORITY == thread.getPriority()) {
                            thread.setPriority(Thread.NORM_PRIORITY);
                        }
                        //设置join
                        //设置是否是守护线程
                        return thread;
                    }
                },
                new RejectedExecutionHandler() {
                    /**
                     * ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
                     * ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
                     * ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
                     * ThreadPoolExecutor.CallerRunsPolicy：只要线程池不关闭，该策略直接在调用者线程中，运行当前被丢弃的任务
                     * 个人认为这4中策略不友好，最好自己定义拒绝策略，实现RejectedExecutionHandler接口
                     * ---------------------
                     * 作者：kusedexingfu
                     * 来源：CSDN
                     * 原文：https://blog.csdn.net/kusedexingfu/article/details/72491864
                     * 版权声明：本文为博主原创文章，转载请附上博文链接！
                     * @param r
                     * @param executor
                     */
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        //打印日志
                        //补偿
                        //兜底策略
                        System.out.println("自定义拒绝策略");
                    }
                }
        );
    }
}
