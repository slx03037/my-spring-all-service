package com.slx.spring.model;

import lombok.Data;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-02-02 20:56
 **/
@Data
public class WsParam<T> {
    private String method;
    private T param;
}
