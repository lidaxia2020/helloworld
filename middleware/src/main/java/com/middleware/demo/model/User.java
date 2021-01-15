package com.middleware.demo.model;


import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author lijiannan
 * @version 1.0
 * @date 2021/1/11 11:43
 */
@Data
@ToString
public class User{

    private Integer id;

    private String name;

    private String userName;

    public User() {
    }

    public User(Integer id, String name, String userName) {
        this.id = id;
        this.name = name;
        this.userName = userName;
    }
}
