package com.slx.springboot;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-06 16:35
 **/
@SpringBootApplication
@EnableCaching
@EnableAsync
@MapperScan(basePackages = {"com.slx.springboot.mybatis.mapper"})
//@EnableBatchProcessing
public class SpringbootMainApplication {
    public static void main(String[]args){
        SpringApplication.run(SpringbootMainApplication.class,args);
    }
}
