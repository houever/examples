package com.thread.test;

import lombok.extern.slf4j.Slf4j;

/***
 * 守护线程与非守护线程（用户线程）
 * 通过thread.setDaemon(false);设置：false：设置为用户线程，true设置为守护线程
 * 守护线程会随着主线程的消亡而死亡
 */
@Slf4j
public class TestThread1 {


    public static void main(String[] args) {

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(100);
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                        System.out.println("我是子线程...");
                    }
                }
            });
            thread.setDaemon(false);
            thread.start();
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(100);
                } catch (Exception e) {

                }
                System.out.println("我是主线程");
            }
            System.out.println("主线程执行完毕!");
        }

    }


