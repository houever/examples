package com.singleton.doublecheck;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class MySingletonTest {

    public static void main(String[] args) {
        try {
            //使用class。forname加载类
            Class<?> aClass = Class.forName("com.singleton.doublecheck.MySingleton");

            //获取类的构造
            Constructor<?> declaredConstructor = aClass.getDeclaredConstructor();

            //设置可访问为true
            declaredConstructor.setAccessible(true);

            //得到类的对象
            MySingleton mySingleton = (MySingleton) declaredConstructor.newInstance();

            System.out.println(mySingleton);

        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {

            e.printStackTrace();
        } catch (IllegalAccessException e) {

            e.printStackTrace();
        } catch (InstantiationException e) {

            e.printStackTrace();
        }
    }
}
