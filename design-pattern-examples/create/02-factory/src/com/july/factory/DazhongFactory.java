package com.july.factory;

public class DazhongFactory implements ICarFactory {
    @Override
    public void createCar() {
        System.out.println("生产大众汽车");
    }
}
