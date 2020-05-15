package think.lambda;

import think.classinfo.Interface;

import java.util.Arrays;
import java.util.function.*;

/**
 * @Auther lidaxia
 * @Date 2020-05-15 21:00
 */
public class MethodRefrenceDemo {

    public static void main(String[] args) {

        // 方法引用
        Consumer<String> consumer = System.out::println;
        consumer.accept("方法引用");

        // 静态方法引用
        Consumer<Dog> dogConsumer = Dog::bark;
        dogConsumer.accept(new Dog());

        Dog dog = new Dog();
        //非静态方法，使用对象实例
        Function<Integer,Integer> function = dog::eat;
        UnaryOperator<Integer> unaryOperator = dog::eat;


        // 使用类名来方法引用
        BiFunction<Dog,Integer,Integer> biFunction = Dog::eat;

        System.out.println(function.apply(3));

        // 构造方法引用
        Supplier<Dog> supplier = Dog::new;

        // 带参数的构造方法引用
        Function<String,Dog> function1 = Dog::new;

    }
}

class Dog{
    private String name = "test";

    public Dog() {
    }

    public Dog(String name) {
        this.name = name;
    }

    public static void bark(Dog dog){
        System.out.println(dog + "咆哮");
    }

    @Override
    public String toString() {
        return this.name;
    }

    public Integer eat(int num){
        System.out.println("吃了 " + num + "包");
        return 0;
    }
}
