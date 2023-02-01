package com.slx.springboot.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-30 11:05
 **/
@Data
public class Permission implements Serializable {
    private static final long serialVersionUID = 9059415047949857959L;
    private Integer id;
    private String url;
    private String name;
}
