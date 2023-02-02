package com.slx.spring.model;

import lombok.Data;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-02-02 20:57
 **/
@Data
public class WsResponse<T> {
    private T result;
}
