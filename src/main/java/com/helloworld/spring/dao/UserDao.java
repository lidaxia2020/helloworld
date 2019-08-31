package com.helloworld.spring.dao;

import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    private boolean flag;

    public void add(){
        System.out.println("增加");
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
