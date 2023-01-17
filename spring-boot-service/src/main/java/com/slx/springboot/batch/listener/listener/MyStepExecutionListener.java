package com.slx.springboot.batch.listener.listener;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.stereotype.Component;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-17 10:40
 **/
@Component
public class MyStepExecutionListener {
    @BeforeStep
    public void breforeStep(StepExecution stepExecution) {
        System.out.println("before step execute: " + stepExecution.getStepName());
    }

    @AfterStep
    public void afterStep(StepExecution stepExecution) {
        System.out.println("after step execute: " + stepExecution.getStepName());
    }
}
