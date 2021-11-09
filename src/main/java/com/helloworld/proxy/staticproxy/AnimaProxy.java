package com.helloworld.proxy.staticproxy;

import com.helloworld.proxy.staticproxy.Anima;

/**
 * 代理对象，静态代理
 * @author daxia li
 * @date 2019/3/29 16:44
 */
public class AnimaProxy implements Anima {

    private Dog target;

    public AnimaProxy(Dog target) {
        this.target = target;
    }

    @Override
    public void action() {
        System.out.println("开始事务...");
        target.action();//执行目标对象的方法
        System.out.println("提交事务...");
    }
}
