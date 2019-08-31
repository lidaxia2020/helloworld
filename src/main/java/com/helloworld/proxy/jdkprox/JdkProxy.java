package com.helloworld.proxy.jdkprox;

import com.helloworld.proxy.staticproxy.Anima;
import com.helloworld.proxy.staticproxy.Dog;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author daxia li
 * @time 2019/8/4
 */
public class JdkProxy implements InvocationHandler {

    public Object object;

    public JdkProxy(Object o){
        this.object = o;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("我是事务前");
        Object object = method.invoke(this.object,args);
        System.out.println("我是事务后");
        return object;
    }

    public static void main(String[] args) {
        //目标对象
        Dog target = new Dog();

        //代理对象,把目标对象传给代理对象,建立代理关系
        JdkProxy jdkProxy = new JdkProxy(target);
        //通过jdk动态代理 通过反射机制生成代理
        Anima anima = (Anima) Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),jdkProxy);

        anima.action();//执行的是代理的方法
    }

}
