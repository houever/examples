package com.singleton.hungry;

import com.singleton.lazy.LazySingleton;

/***
 * 饿汉单例模式
 */
public class HungrySingleton {

    private static HungrySingleton hungrySingleton = new HungrySingleton();

    private HungrySingleton(){};

    public static HungrySingleton getHungrySingleton(){

        return hungrySingleton;

    }

}
