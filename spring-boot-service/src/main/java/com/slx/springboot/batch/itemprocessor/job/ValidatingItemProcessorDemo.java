package com.slx.springboot.batch.itemprocessor.job;

import com.slx.springboot.common.entity.BatchData;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-17 10:33
 **/
@Component
public class ValidatingItemProcessorDemo {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private ListItemReader<BatchData> simpleReader;

    @Bean
    public Job validatingItemProcessorJob() {
        return jobBuilderFactory.get("validatingItemProcessorJob")
                .start(step())
                .build();
    }

    private Step step() {
        return stepBuilderFactory.get("step")
                .<BatchData, BatchData>chunk(2)
                .reader(simpleReader)
                .processor(validatingItemProcessor())
                .writer(list -> list.forEach(System.out::println))
                .build();
    }

    private ValidatingItemProcessor<BatchData> validatingItemProcessor() {
        ValidatingItemProcessor<BatchData> processor = new ValidatingItemProcessor<>();
        processor.setValidator(value -> {
            // 对每一条数据进行校验
            if ("".equals(value.getField3())) {
                // 如果field3的值为空串，则抛异常
                throw new ValidationException("field3的值不合法");
            }
        });
        return processor;
    }
}
