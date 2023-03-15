package com.shenlx.xinwen.springbootredis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-15 21:50
 **/
@SpringBootApplication
@MapperScan(basePackages = {"com.shenlx.xinwen.springbootredis.mapper"})
public class SpringBootRedisApplication {
    public static void main(String[]args){
        SpringApplication.run(SpringBootRedisApplication.class,args);
    }
}
