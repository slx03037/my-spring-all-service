package com.slx.springboot.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-30 11:06
 **/
@Data
public class Role implements Serializable {
    private static final long serialVersionUID = 4706885815767617183L;
    private Integer id;
    private String name;
    private String memo;
}
