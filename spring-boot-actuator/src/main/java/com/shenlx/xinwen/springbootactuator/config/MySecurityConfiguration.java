package com.shenlx.xinwen.springbootactuator.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-22 10:23
 **/
@Configuration(proxyBeanMethods = false)
public class MySecurityConfiguration {
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
        httpSecurity.requestMatcher(EndpointRequest.toAnyEndpoint())
                .authorizeRequests((requests)->requests.anyRequest().hasRole("ENDPOINT_AMDIN"));
        httpSecurity.httpBasic();
        return httpSecurity.build();
    }
}
