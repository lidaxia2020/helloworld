package com.helloworld;

import com.helloworld.spring.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloWorldApplication {

    public static void main(String[] args){

        //1 先加载spring容器
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-4.xml");

        System.out.println("spring容器加载");

        UserService userService = (UserService) applicationContext.getBean("userService");

        userService.add();

    }


}
