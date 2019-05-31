package com.singleton.internalclass;

/**
 * 静态内部类的方式创建单例
 *
 * 优势：兼顾了懒汉模式的内存优化（使用时才初始化）以及饿汉模式的安全性（不会被反射入侵）。
 *
 * 劣势：需要两个类去做到这一点，虽然不会创建静态内部类的对象，但是其 Class 对象还是会被创建，而且是属于永久带的对象。
 */
public class MySingleton {


    public static class MySingletonInstance{

        private static final MySingleton SINGLETON = new MySingleton();

    }

    private static MySingleton getInstance(){

        return MySingletonInstance.SINGLETON;

    }

}


