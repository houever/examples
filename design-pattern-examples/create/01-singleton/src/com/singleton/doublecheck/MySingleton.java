package com.singleton.doublecheck;

import javax.xml.stream.FactoryConfigurationError;

/**
 * 双检锁实现单例模式
 */
public class MySingleton {

    private MySingleton mySingleton;

    private static boolean flag = true;


    private MySingleton() {
        if (flag == false) {
            flag = !flag;
        } else {
            throw new RuntimeException("单例模式被侵犯");
        }
    }

    public MySingleton getMySingleton() {

        if (mySingleton == null) {

            synchronized (this) {

                if (mySingleton == null) {

                    mySingleton = new MySingleton();

                }
            }
        }
        return mySingleton;
    }
}
