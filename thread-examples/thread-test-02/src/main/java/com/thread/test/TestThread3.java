package com.thread.test;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * 优先级
 * 现代操作系统基本采用时分的形式调度运行的线程，
 * 线程分配得到的时间片的多少决定了线程使用处理器资源的多少，
 * 也对应了线程优先级这个概念。在JAVA线程中，通过一个int priority来控制优先级，
 * 范围为1-10，其中10最高，默认值为5。下面是源码（基于1.8）中关于priority的一些量和方法。
 */
@Slf4j
public class TestThread3 {


    public static void main(String[] args) throws InterruptedException {
        priorityThread thread = new priorityThread();
        Thread t1 = new Thread(thread);
        Thread t2 = new Thread(thread);
        t1.setPriority(1);
        t2.setPriority(10);
        t1.start();
        t2.start();

    }

   static class priorityThread implements Runnable{

        @Override
        public void run() {
            for(int i=0;i<=100;i++){
                System.out.println(Thread.currentThread().getName()+"=======>"+i);
            }
        }
    }
}


