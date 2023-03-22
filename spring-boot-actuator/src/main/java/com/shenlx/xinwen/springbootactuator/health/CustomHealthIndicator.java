package com.shenlx.xinwen.springbootactuator.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * @program: my-spring-all-service
 * @description: 自定义HealthIndicator
 * @author: shenlx
 * @create: 2023-03-22 10:38
 **/
@Component
public class CustomHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        int errorCode = check();
        if (errorCode!=0) {
            return Health.down().withDetail("Error Code", errorCode).build();
        }
        return Health.up().build();
    }

    private int check() {
        // perform some specific health check
        return 2;
    }
}
