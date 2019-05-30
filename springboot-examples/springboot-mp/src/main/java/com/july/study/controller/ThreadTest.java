package com.july.study.controller;

import com.july.study.model.Users;

import java.util.*;

public class ThreadTest {
    public static void main(String[] args) {


        System.out.println("我是主线程");

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("我是子线程");
            }
        }).start();
        Users u1 = new Users();
        Users u2 = new Users();

        System.out.println(u1.equals(u2));
    }
}
