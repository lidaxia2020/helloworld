package com.helloworld.spring.entity;

public class UserEntity {

    private String name;

    private int age;

    public UserEntity() {
        System.out.println("UserEntity 初始化");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
