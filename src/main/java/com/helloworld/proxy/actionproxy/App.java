package com.helloworld.proxy.actionproxy;


import com.helloworld.proxy.staticproxy.Dog;

/**
 * @author daxia li
 * @date 2019/3/29 17:00
 */
public class App {
    public static void main(String[] args) {
        //目标对象
        Dog target = new Dog();
        System.out.println(target.getClass());

        //代理对象,把目标对象传给代理对象,建立代理关系
        Dog proxy = (Dog)new ProxyFactory(target).getProxyInstance();
        System.out.println(proxy.getClass());

        proxy.action();//执行的是代理的方法
    }
}
