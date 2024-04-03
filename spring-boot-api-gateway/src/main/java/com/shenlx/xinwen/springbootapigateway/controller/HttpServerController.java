package com.shenlx.xinwen.springbootapigateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shenlx
 * @description
 * @date 2023/12/28 22:31
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class HttpServerController {
    @GetMapping("/http-server/ping")
    public String ping() {
        log.info("HttpServerController.ping.execute....");
        return "pong";
    }
}
