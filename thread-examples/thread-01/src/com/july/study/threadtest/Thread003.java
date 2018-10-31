package com.july.study.threadtest;


public class Thread003 {

    public static void main(String[] args) {
        Thread thread = new Thread(()->{
            for (int i = 0; i < 10; i++) {
                System.out.println(" 子 i:" + i);
            }
        });

        thread.start();
        for (int i = 0; i < 10; i++) {
            System.out.println(" 主 i:" + i);
        }
    }

}
