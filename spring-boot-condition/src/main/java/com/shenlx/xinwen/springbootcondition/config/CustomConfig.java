package com.shenlx.xinwen.springbootcondition.config;

import com.shenlx.xinwen.springbootcondition.domain.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-04-12 16:42
 **/
@Configuration
public class CustomConfig {
    /**
     * 在Windows环境下注入的Bean为winP
     * @return
     */
    @Bean("winP")
    @Conditional(value = {WindowsCondition.class})
    public Person personWin(){
        return new Person();
    }
    /**
     * 在Linux环境下注入的Bean为LinuxP
     * @return
     */
    @Bean("LinuxP")
    @Conditional(value = {LinuxCondition.class})
    public Person personLinux(){
        return new Person();
    }

}
