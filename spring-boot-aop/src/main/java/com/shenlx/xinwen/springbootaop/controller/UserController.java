package com.shenlx.xinwen.springbootaop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-12 20:16
 **/
@RestController
public class UserController {

    @RequestMapping("/first")
    public Object first() {
        return "first com.shenlx.xinwen.swagger2.api.controller";
    }

    @RequestMapping("/doError")
    public Object error() {
        return 1 / 0;
    }

    @RequestMapping("/second")
    public Object second() {
        return "second com.shenlx.xinwen.swagger2.api.controller";
    }

}
