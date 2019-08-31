package com.helloworld.spring.service;

import com.helloworld.spring.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    private UserDao userDao;

    @Autowired
    private Aop aop;

    public void add(){
//        aop.begin();
        System.out.println("service 运行");
        userDao.add();
//        aop.commint();

    }


    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
