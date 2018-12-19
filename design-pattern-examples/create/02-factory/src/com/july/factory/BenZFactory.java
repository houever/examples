package com.july.factory;

public class BenZFactory implements ICarFactory {
    @Override
    public void createCar() {
        System.out.println("生产奔驰汽车");
    }
}
