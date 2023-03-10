package com.slx.springboot.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-10 14:39
 **/
@Data
public class Account implements Serializable {
    private static final long serialVersionUID = -5308784430593569766L;
    private String account;
    private String name;
    private String password;
    private String accountType;
    private String tel;

    public Account(String account, String name, String password, String accountType, String tel) {
        super();
        this.account = account;
        this.name = name;
        this.password = password;
        this.accountType = accountType;
        this.tel = tel;
    }
}
