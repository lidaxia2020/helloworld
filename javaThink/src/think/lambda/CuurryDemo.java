package think.lambda;


import java.util.Arrays;
import java.util.function.Function;

/**
 * 级联表达式和柯里化
 * 柯里化：把多个参数的函数转化成只有一个参数的函数
 * 柯里化的目的：函数标准化
 * 高阶函数
 * @Auther lidaxia
 * @Date 2020-05-15 21:43
 */
public class CuurryDemo {

    public static void main(String[] args) {
        // 实现了级联表达式
        Function<Integer,Function<Integer,Integer>> function = x -> y -> x+y;
        System.out.println(function.apply(1).apply(3));


    }

}
