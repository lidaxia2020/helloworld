package main.java.com.helloworld.zhujie;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @interface 定义注解
 * @Target  允许在哪里使用的范围
 * @Retention 表示允许反射获取信息
 * @author daxia li
 * @time 2019/8/4
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationTest {

    String value() default "";
}

class AnnoDemo{

    @AnnotationTest("test")
    public void test(){

    }
}
