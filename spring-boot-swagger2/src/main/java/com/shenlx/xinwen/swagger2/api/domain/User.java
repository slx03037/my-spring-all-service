package com.shenlx.xinwen.swagger2.api.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-21 11:11
 **/
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 2127778744062753183L;
    private Long id;
    private String name;
    private Integer age;
}
