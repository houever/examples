package com.singleton.enumsingleton;

/**
 *  使用枚举创建单例的好处：
 *      实现简单，因为枚举类本身就是单例，一方面避免了懒汉延迟加载，一方面有避免了使用反射和序列化漏洞
 */
public class EnumSingleton {

    public static void main(String[] args) {

        System.out.println(EnumSingleton.getEnumSingleton()==EnumSingleton.getEnumSingleton());

    }

    public static EnumSingleton getEnumSingleton(){
        return SingletonEnum.INSTANCE.enumSingleton;
    }


    //创建枚举单例
    static enum SingletonEnum{

        INSTANCE;

        private EnumSingleton enumSingleton;

        private SingletonEnum(){
            enumSingleton = new EnumSingleton();
        }



    }

}
