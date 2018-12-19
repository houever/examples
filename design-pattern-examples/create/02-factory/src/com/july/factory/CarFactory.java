package com.july.factory;

/**
 * 决定创建哪个对象的工厂
 */
public class CarFactory {

    public ICarFactory iCarFactory(String carType){
        if(carType.equalsIgnoreCase("大众")){
            return new DazhongFactory();
        }else if(carType.equalsIgnoreCase("奔驰")){
            return new BenZFactory();
        }
        return null;
    }
}
