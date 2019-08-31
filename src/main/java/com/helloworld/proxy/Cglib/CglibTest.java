package com.helloworld.proxy.Cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import com.helloworld.proxy.staticproxy.Anima;
import com.helloworld.proxy.staticproxy.Dog;

import java.lang.reflect.Method;

/**
 * @author daxia li
 * @time 2019/8/4
 */
public class CglibTest implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("我是事务前");
        Object object = methodProxy.invokeSuper(o,objects);
        System.out.println("我是事务后");
        return object;
    }


    public static void main(String[] args) {
        //代理对象,把目标对象传给代理对象,建立代理关系
        CglibTest cglibTest = new CglibTest();
        //使用Asm框架生产代理
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Dog.class);
        enhancer.setCallback(cglibTest);
        Object o = enhancer.create();
        Anima anima = (Anima) o;
        anima.action();
    }


}
