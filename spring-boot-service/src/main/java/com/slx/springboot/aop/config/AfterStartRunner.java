package com.slx.springboot.aop.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-06 19:31
 **/

@Component
@Order(1)
@Slf4j
public class AfterStartRunner implements CommandLineRunner {

    // private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(String... args) {
        log.debug("after start debug...");
        log.info("after start info...");

    }

}