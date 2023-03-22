package com.shenlx.xinwen.springbootactuator.controller;

import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @program: my-spring-all-service
 * @description: 所有开放的接口中增加了date
 * @author: shenlx
 * @create: 2023-03-22 10:33
 **/
@RestController("custom")
@WebEndpoint(id="date")
public class CustomEndpointController {
    @ReadOperation
    public ResponseEntity<String> currentDate() {
        return ResponseEntity.ok(LocalDateTime.now().toString());
    }
}
