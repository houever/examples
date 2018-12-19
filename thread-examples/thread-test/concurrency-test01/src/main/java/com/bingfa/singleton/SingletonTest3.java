package com.bingfa.singleton;

import com.bingfa.annotations.ThreadSafe;

/**
 * 饿汉模式是线程安全的
 */
@ThreadSafe
public class SingletonTest3 {


    private SingletonTest3(){

    }


    /**
     * volatile 的双重检测机制实现单例的安全发布对象
     */
    private static volatile SingletonTest3 singletonTest3;

    /**
     * 静态工厂方法
     * @return
     */
    public static SingletonTest3 getSingletonTest1(){
        if(singletonTest3 == null){
            synchronized (SingletonTest3.class){
                return new SingletonTest3();
            }
        }
        return singletonTest3;
    }

}
