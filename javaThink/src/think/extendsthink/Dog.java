package think.extendsthink;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Auther lidaxia
 * @Date 2020-04-16 23:26
 */
public class Dog extends Anima
{

    private FinalData f = new FinalData();

    public Dog() {
        System.out.println("Dog");
    }


    @Override
    void speak() {
        super.speak();
        System.out.println("dog speak");
    }

    public void run(){
        System.out.println("dog run");
    }

    public static void main(String[] args) {
        /**
         * 子类可以继承父类的功能，但父类只能感知自己拥有的方法
         */
        Anima anima = new Dog();
        anima.speak();
        String a ="a";
        a.equals("a");
        int i = 1000;
        System.out.println(Integer.toBinaryString(i));
    }
}


