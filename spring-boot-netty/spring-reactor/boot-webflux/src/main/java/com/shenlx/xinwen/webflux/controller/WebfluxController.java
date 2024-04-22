package com.shenlx.xinwen.webflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author shenlx
 * @description
 * @date 2024/4/18 11:11
 */
@RestController
public class WebfluxController {
    @GetMapping("/test/1")
    public Mono<String> test1(){
        return Mono.just("result");
    }
}
