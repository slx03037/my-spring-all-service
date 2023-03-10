package com.slx.springboot.batch.itemprocessor.job;

import com.slx.springboot.batch.itemprocessor.processor.TestDataTransformItemPorcessor;
import com.slx.springboot.common.entity.BatchData;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-17 10:32
 **/
@Component
public class TestDataTransformItemPorcessorDemo {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private ListItemReader<BatchData> simpleReader;
    @Autowired
    private TestDataTransformItemPorcessor testDataTransformItemPorcessor;

    @Bean
    public Job testDataTransformItemPorcessorJob() {
        return jobBuilderFactory.get("testDataTransformItemPorcessorJob")
                .start(step())
                .build();
    }

    private Step step() {
        return stepBuilderFactory.get("step")
                .<BatchData, BatchData>chunk(2)
                .reader(simpleReader)
                .processor(testDataTransformItemPorcessor)
                .writer(list -> list.forEach(System.out::println))
                .build();
    }
}
