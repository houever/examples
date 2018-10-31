package com.july.study.threadtest;


public class Thread004 {

    public static void main(String[] args) {
        Thread thread = new Thread(()->{
            for (int i = 0; i < 10; i++) {

                //线程休眠
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(" 子 i:" + i);
            }
        });

        thread.start();
    }

}
