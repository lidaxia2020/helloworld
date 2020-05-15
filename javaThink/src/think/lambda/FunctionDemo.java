package think.lambda;

import think.classinfo.Interface;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *  接口                输入参数          返回类型                        说明
 *  Predicate<T>          T               boolean                       断言
 *  Consumer<T>           T                /                            消费一个数据
 *  Function<T,R>         T                R                           输入T输出R的函数
 *  Supplier<T>           /                T                            提供一个数据
 *  UnaryOperator<T>      T                T                            一元函数
 *  BiFunction<T,U,R>    (T,U)             R                            2个输入的函数
 *  BinaryOperator<T>    (T,T)             T                            二元函数（输入输出类型相同）
 *
 *
 * @Auther lidaxia
 * @Date 2020-05-15 20:41
 */
public class FunctionDemo {

    public static void main(String[] args) {
        MyMoney me = new MyMoney(99999);

        Function<Integer,String> function = i-> new DecimalFormat("#,###").format(i);
        me.print(function.andThen(s-> "rmb  " + s));

        // 断言
        Predicate<Integer> predicate = i -> i > 0;
        System.out.println(predicate.test(-9));

        // 消费函数
        Consumer<String> consumer = s -> System.out.println(s);
        consumer.accept("输入的数据");

    }
}

class MyMoney{
    private int i;

    public MyMoney(int i) {
        this.i = i;
    }

    public void print(Function<Integer,String> me){
        System.out.println("我的钱： " + me.apply(this.i));
    }
}