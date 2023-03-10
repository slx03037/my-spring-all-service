package com.slx.springboot.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-16 18:55
 **/
@Data
public class BatchData implements Serializable {
    private static final long serialVersionUID = -2339788125852813522L;
    private int id;
    private String field1;
    private String field2;
    private String field3;
}
