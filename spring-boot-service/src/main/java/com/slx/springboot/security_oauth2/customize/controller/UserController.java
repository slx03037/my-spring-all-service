package com.slx.springboot.security_oauth2.customize.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-02-01 15:24
 **/
@RestController
public class UserController {
    @GetMapping("index")
    public Object index(@AuthenticationPrincipal Authentication authentication){
        return authentication;
    }
}
