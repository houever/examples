package com.july.study.dis.review;

import java.util.concurrent.atomic.AtomicLong;

public class Review {

    public static void main(String[] args) {


        AtomicLong atomicLong = new AtomicLong(0);
        //expect期望是什么，最终修改成update
        //atomicLong.compareAndSet(expect,update);
        boolean b = atomicLong.compareAndSet(3, 2);
        System.out.println(b);
        System.out.println(atomicLong.get());
    }
}
