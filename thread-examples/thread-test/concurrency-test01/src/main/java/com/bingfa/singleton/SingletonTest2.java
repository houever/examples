package com.bingfa.singleton;

import com.bingfa.annotations.ThreadSafe;

/**
 * 懒汉模式
 */
@ThreadSafe
public class SingletonTest2 {


    private SingletonTest2(){

    }


    private static volatile SingletonTest2 singletonTest1;

    public static synchronized SingletonTest2 getSingletonTest1(){
        if(singletonTest1 == null){
            singletonTest1 =  new SingletonTest2();
        }
        return singletonTest1;
    }

}
