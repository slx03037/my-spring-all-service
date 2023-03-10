package com.slx.springboot.lifecycle.config;

import com.slx.springboot.lifecycle.domain.Fish;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-17 22:20
 **/
@Configuration
public class WebConfig {
    // @Scope("prototype")
    // @Bean(initMethod = "init", destroyMethod = "destory")
    // public User user() {
    //     return new User();
    // }

    // @Bean
    // public Bird bird() {
    //     return new Bird();
    // }

    @Bean
    public Fish fish(){
        return new Fish();
    }

    @Bean
    public MyBeanPostProcessor myBeanPostProcessor () {
        return new MyBeanPostProcessor();
    }
}
