package com.helloworld.shejimoshi;

/**
 * @author daxia li
 * @time 2019/8/4
 */
public class SingletonTest1 {

    private static final SingletonTest1 singletonTest1 = new SingletonTest1();

    private SingletonTest1(){
    }

    public static SingletonTest1 getSingletonTest1(){
        return singletonTest1;
    }


    public static void main(String[]args){
        SingletonTest1 singletonTest1 = getSingletonTest1();
        SingletonTest1 singletonTest2 = getSingletonTest1();

        System.out.println(singletonTest1 == singletonTest2);
    }
}
