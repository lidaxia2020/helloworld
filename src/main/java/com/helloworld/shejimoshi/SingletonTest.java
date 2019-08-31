package com.helloworld.shejimoshi;

/**
 * @author daxia li
 * @time 2019/8/4
 */
public class SingletonTest {

    private static SingletonTest singletonTest;

    private SingletonTest(){

    }

    /**
     * 懒汉式
     * @return
     */
    public static synchronized SingletonTest getSingletonTest(){
        if(singletonTest == null){
            synchronized(SingletonTest.class){
                singletonTest = new SingletonTest();
            }

        }
        return singletonTest;
    }

    public static void main(String[]args){
        SingletonTest singletonTest1 = getSingletonTest();
        SingletonTest singletonTest2 = getSingletonTest();

        System.out.println(singletonTest1 == singletonTest2);
    }
}
