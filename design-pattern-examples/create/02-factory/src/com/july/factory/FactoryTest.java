package com.july.factory;

public class FactoryTest {

    public static void main(String[] args) {

        CarFactory carFactory = new CarFactory();

        ICarFactory dz = carFactory.iCarFactory("大众");

        dz.createCar();

    }
}
