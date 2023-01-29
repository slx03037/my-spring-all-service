package com.slx.springboot.actuator;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-28 10:51
 **/
@RestController
public class ActuatorController {
    @RequestMapping("/")
    String index() {
        return "hello spring boot";
    }
}
