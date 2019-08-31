package com.helloworld.shejimoshi.factory;

/**
 * @author daxia li
 * @time 2019/8/4
 */
public class CarFactory {

    public static Car createCar(int catType){
        Car car = null;
        switch (catType){
            case 1:
                car = new Aodi();
                break;
            case 2:
                car = new Benchi();
                break;
            default:
                break;
        }
        return car;
    }
}
