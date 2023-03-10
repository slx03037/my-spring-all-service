package com.slx.springboot.shiro.jwt.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-30 22:46
 **/
@Slf4j
public class PrintRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) {
        log.info("Provided by handsome 帅比裙主，详情见readme.md");
    }
}
