package com.singleton.lazy;

/**
 * 懒汉单例设计模式
 */
public class LazySingleton {

    /*类初始化的时候就创建对象，占用内存*/
    private static LazySingleton lazySingleton;

    //私有化构造
    private LazySingleton(){};

    //公有的创建对象的方法
    public static LazySingleton getInstance(){

        if(lazySingleton == null){

            lazySingleton = new LazySingleton();

        }
        return lazySingleton;
    }

}
