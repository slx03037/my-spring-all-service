package com.slx.springboot.batch.itemprocessor.job;

import com.slx.springboot.batch.itemprocessor.processor.TestDataFilterItemProcessor;
import com.slx.springboot.batch.itemprocessor.processor.TestDataTransformItemPorcessor;
import com.slx.springboot.common.entity.BatchData;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-17 10:30
 **/
@Component
public class CompositeItemProcessorDemo {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private ListItemReader<BatchData> simpleReader;
    @Autowired
    private TestDataFilterItemProcessor BatchDataFilterItemProcessor;
    @Autowired
    private TestDataTransformItemPorcessor BatchDataTransformItemPorcessor;

    @Bean
    public Job compositeItemProcessorJob() {
        return jobBuilderFactory.get("compositeItemProcessorJob")
                .start(step())
                .build();
    }

    private Step step() {
        return stepBuilderFactory.get("step")
                .<BatchData, BatchData>chunk(2)
                .reader(simpleReader)
                .processor(compositeItemProcessor())
                .writer(list -> list.forEach(System.out::println))
                .build();
    }

    // CompositeItemProcessor组合多种中间处理器
    private CompositeItemProcessor<BatchData, BatchData> compositeItemProcessor() {
        CompositeItemProcessor<BatchData, BatchData> processor = new CompositeItemProcessor<>();
        List<ItemProcessor<BatchData, BatchData>> processors = Arrays.asList(BatchDataFilterItemProcessor, BatchDataTransformItemPorcessor);
        // 代理两个processor
        processor.setDelegates(processors);
        return processor;
    }
}
