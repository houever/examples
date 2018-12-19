package com.thread.test;

import com.sun.xml.internal.ws.api.server.ThreadLocalContainerResolver;
import lombok.extern.slf4j.Slf4j;

/**
 * 多线程的执行顺序
 * 通过thread.join()方法
 * 例如：两个线程 t1,t2，在t2里面 加入 t1.join 会让t1 先执行
 */
@Slf4j
public class TestThread2 {


    public static void main(String[] args) {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println("t1");
                System.out.println(Thread.currentThread().getName());
            }
        });
        System.out.println(Thread.currentThread().getName());
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t2");
                System.out.println(Thread.currentThread().getName());
            }
        });
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    t2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t3");
                System.out.println(Thread.currentThread().getName());
            }
        });
        System.out.println(Thread.currentThread().getName());
        t1.start();
        t1.getName();
        t2.start();
        t3.start();
        }

    }


