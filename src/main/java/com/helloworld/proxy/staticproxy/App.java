package com.helloworld.proxy.staticproxy;

/**
 *  测试
 * @author lijn
 * @date 2019/3/29 16:46
 */
public class App {

    public static void main(String[] args) {
        //目标对象
        Dog target = new Dog();

        //代理对象,把目标对象传给代理对象,建立代理关系
        AnimaProxy proxy = new AnimaProxy(target);

        proxy.action();//执行的是代理的方法
    }
}
