package com.helloworld.reflect;

import com.helloworld.pojo.Student;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *  反射学习
 * @author daxia li
 * @date 2019/3/29 15:06
 */
public class Fields {

    public static void main(String[] args) {

        try {
            //获取类对象

            Class o = Class.forName("main.java.com.helloworld.pojo.Student");

            /*
             * 获取成员变量并调用：
             *
             * 1.批量的
             * 		1).Field[] getFields():获取所有的"公有字段"
             * 		2).Field[] getDeclaredFields():获取所有字段，包括：私有、受保护、默认、公有；
             * 2.获取单个的：
             * 		1).public Field getField(String fieldName):获取某个"公有的"字段；
             * 		2).public Field getDeclaredField(String fieldName):获取某个字段(可以是私有的)
             *
             * 	 设置字段的值：
             * 		Field --> public void set(Object obj,Object value):
             * 					参数说明：
             * 					1.obj:要设置的字段所在的对象；
             * 					2.value:要为字段设置的值；
             *
             */
            //获取字段
            System.out.println("**************获取所有公有的字段********************************");
            Field[] fieldArray = o.getFields();
            for(Field field : fieldArray){
                System.out.println(field);
            }

            System.out.println();
            System.out.println("**************获取所有的字段(包括私有、受保护、默认的)********************************");
            fieldArray = o.getDeclaredFields();
            for(Field field : fieldArray){
                System.out.println(field);
            }

            System.out.println();
            System.out.println("**************获取公有字段********************************");
            Field f = o.getField("name");
            System.out.println(f);
            //获取一个对象
            //产生Student对象--》Student stu = new Student();
            Object obj = o.getConstructor().newInstance();
            //为字段设置值
            //为Student对象中的name属性赋值--》stu.name = "刘德华"
            f.set(obj, "刘德华");
            //验证
            Student stu = (Student)obj;
            System.out.println("验证姓名：" + stu.name);

            System.out.println();
            System.out.println("**************获取私有字段****并调用********************************");
            f = o.getDeclaredField("phoneNum");
            System.out.println(f);
            //暴力反射，解除私有限定
            f.setAccessible(true);
            f.set(obj, "18888889999");
            System.out.println("验证电话：" + stu);




        /*
         * 获取成员方法并调用：
         *
         * 1.批量的：
         * 		public Method[] getMethods():获取所有"公有方法"；（包含了父类的方法也包含Object类）
         * 		public Method[] getDeclaredMethods():获取所有的成员方法，包括私有的(不包括继承的)
         * 2.获取单个的：
         * 		public Method getMethod(String name,Class<?>... parameterTypes):
         * 					参数：
         * 						name : 方法名；
         * 						Class ... : 形参的Class类型对象
         * 		public Method getDeclaredMethod(String name,Class<?>... parameterTypes)
         *
         * 	 调用方法：
         * 		Method --> public Object invoke(Object obj,Object... args):
         * 					参数说明：
         * 					obj : 要调用方法的对象；
         * 					args:调用方式时所传递的实参；
        ):
         */

            //2.获取所有公有方法
            System.out.println();
            System.out.println("***************获取所有的”公有“方法*******************");
            Method[] methodArray = o.getMethods();
            for(Method m : methodArray){
                System.out.println(m);
            }

            System.out.println();
            System.out.println("***************获取所有的方法，包括私有的*******************");
            methodArray = o.getDeclaredMethods();
            for(Method m : methodArray){
                System.out.println(m);
            }

            System.out.println();
            System.out.println("***************获取公有的show1()方法*******************");
            Method m = o.getMethod("show1", String.class);
            System.out.println(m);
            //实例化一个Student对象
            obj = o.getConstructor().newInstance();
            //解除私有限定
            m.setAccessible(true);

            System.out.println();
            System.out.println("***************获取私有的show4()方法******************");
            m = o.getDeclaredMethod("show4", int.class);
            System.out.println(m);
            //解除私有限定
            m.setAccessible(true);
            //需要两个参数，一个是要调用的对象（获取有反射），一个是实参
            Object result = m.invoke(obj, 30);
            System.out.println("返回值：" + result);




        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
