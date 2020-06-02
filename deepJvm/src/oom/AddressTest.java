package oom;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther lidaxia
 * @Date 2020-05-25 7:37
 */
public class AddressTest {

    public static void main(String[] args) {
        Thread thread1 = new Thread(()->{
            AddressTest addressTest = new AddressTest();
            System.out.println("hashCode = " + addressTest.hashCode());
        });
        Thread thread2 = new Thread(()->{
            AddressTest addressTest = new AddressTest();
            System.out.println("hashCode = " + addressTest.hashCode());
        });
       thread1.start();
       thread2.start();
    }

}
