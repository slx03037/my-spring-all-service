package com.shenlx.xinwen.springbootfilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
/**
 * 要想 @WebFilter 注解生效，需要在配置类上标注另外一个注解 @ServletComponentScan 用于扫描使其生效
 */
@ServletComponentScan(value = {"com.example.springbootintercept.filter"})
public class SpringBootFilterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootFilterApplication.class, args);
    }

}
