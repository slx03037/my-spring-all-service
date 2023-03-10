package com.slx.springboot.aop.controller;

import com.slx.springboot.aop.aspect.LogMark;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-06 19:01
 **/
@RestController
public class AspectController {
    @RequestMapping("/first")
    public Object first() {
        return "first controller";
    }

    @RequestMapping("/doError")
    public Object error() {
        return 1 / 0;
    }

    @RequestMapping("/second")
    @LogMark(desc = "second")
    public Object second() {
        return "second controller";
    }
}
