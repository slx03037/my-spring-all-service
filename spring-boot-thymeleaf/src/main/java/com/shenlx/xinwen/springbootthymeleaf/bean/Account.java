package com.shenlx.xinwen.springbootthymeleaf.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-12 20:43
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Account implements Serializable {
    private static final long serialVersionUID = -7348015678595168244L;
    private String account;
    private String name;
    private String password;
    private String accountType;
    private String tel;
}
