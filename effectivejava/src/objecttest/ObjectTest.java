package objecttest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther lidaxia
 * @Date 2020-05-15 16:55
 */
public class ObjectTest {

    public static void main(String[] args) {
        ObjectTest objectTest = new ObjectTest();
        objectTest.test1();
        System.out.println("=================");
        objectTest.test2();
    }


    private void test1(){
        ObjectTest list ;
        for (int i = 0; i < 10; i++){
            list = new ObjectTest();
            System.out.println(list.toString());
        }
    }


    private void test2(){
        for (int i = 0; i < 10; i++){
            ObjectTest list = new ObjectTest();
            System.out.println(list.toString());
        }
    }
}
