package com.shenlx.xinwen.springbootconfig.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-10 11:18
 **/
@Component
@Data
public class PropertiesGetBean {
    @Value("${server.port}")
    private String port;
}
