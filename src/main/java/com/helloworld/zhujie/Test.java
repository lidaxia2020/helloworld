package main.java.com.helloworld.zhujie;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author daxia li
 * @time 2019/8/4
 */
public class Test {

    public static void main(String[] args) throws Exception {
        //1.项目中使用注解，肯定会使用到反射，应用场景jdbc,spring ioc  ，一些注解
        Class<?> c = Class.forName("main.java.com.helloworld.zhujie.UserEntity");
        //2.getAnnotations()该类上用到的注解
        Annotation[] annotations = c.getAnnotations();
        for (Annotation annotation:
                annotations) {
            System.out.println(annotation);
        }


        StringBuffer sb = new StringBuffer("select ");
        Field[] declaredFields = c.getDeclaredFields();
        for (int i =0 ; i < declaredFields.length ; i++) {
            SetProperty setProperty = declaredFields[i].getAnnotation(SetProperty.class);
            String property = setProperty.name();
            sb.append(property);
            if(i == (declaredFields.length-1)){
                sb.append(" from ");
            }else{
                sb.append(",");
            }
        }
        //getAnnotion 获取某个注解对象
        SetTable setTable = c.getAnnotation(SetTable.class);
        System.out.println(setTable.value());
        sb.append(setTable.value());
        System.out.println(sb.toString());
    }
}
