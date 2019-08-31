package com.helloworld.spring.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect//切面标识位
public class Aop {

    @Pointcut("execution(* com.helloworld.spring.service.UserService.add(..))")
    private void pointcut(){

    }

    @Before("pointcut()")
    public void begin(){
        System.out.println("开启事务");

    }


    @After("pointcut()")
    public void commint(){
        System.out.println("事物提交");
    }

    @AfterThrowing("pointcut()")
    public void error(){
        System.out.println("异常通知");
    }

    @AfterReturning("pointcut()")
    public void run(){
        System.out.println("运行通知");
    }

    @Around("pointcut()")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("我是环绕通知-前");
        proceedingJoinPoint.proceed();
        System.out.println("我是环绕通知-后");
    }


}
