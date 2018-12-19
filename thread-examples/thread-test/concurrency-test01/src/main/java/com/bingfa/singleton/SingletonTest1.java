package com.bingfa.singleton;

import com.bingfa.annotations.ThreadSafe;

/**
 * 饿汉模式是线程安全的
 */
@ThreadSafe
public class SingletonTest1 {

    //问题：1私有构造函数没有太多的处理
    private SingletonTest1(){

    }

    //这个类在实际的过程中肯定会被使用
    private static SingletonTest1 singletonTest1 = new SingletonTest1();

    public static SingletonTest1 getSingletonTest1(){
        return singletonTest1;
    }

}
