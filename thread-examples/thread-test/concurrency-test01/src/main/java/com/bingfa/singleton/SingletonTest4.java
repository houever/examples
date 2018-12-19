package com.bingfa.singleton;

import com.bingfa.annotations.Recmooend;
import com.bingfa.annotations.ThreadSafe;

/**
 * 枚举模式：最安全的
 * 不会造成资源的浪费
 *
 */
@Recmooend
@ThreadSafe
public class SingletonTest4 {


    private SingletonTest4(){

    }


    private static volatile SingletonTest4 singletonTest1;

    public static synchronized SingletonTest4 getSingletonTest1(){

        return Singleton.INSTANCE.singletonTest4;
    }

    private enum Singleton{
        INSTANCE;
        private SingletonTest4 singletonTest4;

        Singleton(){
            singletonTest4 = new SingletonTest4();
        }
        public SingletonTest4 getSingletonTest4(){
            return singletonTest4;
        }
    }
}
