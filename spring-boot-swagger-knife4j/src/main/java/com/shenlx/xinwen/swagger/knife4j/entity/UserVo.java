package com.shenlx.xinwen.swagger.knife4j.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-04-24 16:47
 **/
@Data
@Builder
public class UserVo {
    private String name;
    private int age;
    private AddressVo address;
}
