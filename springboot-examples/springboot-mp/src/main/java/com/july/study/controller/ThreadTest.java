package com.july.study.controller;

import sun.applet.Main;

public class ThreadTest {
    public static void main(String[] args) {


        System.out.println("我是主线程");

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("我是子线程");
            }
        }).start();

    }
}
