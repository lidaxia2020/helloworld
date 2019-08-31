package com.helloworld.shejimoshi.factory;

/**
 * @author daxia li
 * @time 2019/8/4
 */
public class Main {

    public static void main(String []args){
        Car aodi = CarFactory.createCar(1);
        Car benchi = CarFactory.createCar(2);

        aodi.run();
        benchi.run();
    }
}
