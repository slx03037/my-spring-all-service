package com.slx.springboot.batch.itemprocessor.processor;

import com.slx.springboot.common.entity.BatchData;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-17 10:24
 **/
@Component
public class TestDataFilterItemProcessor implements ItemProcessor<BatchData, BatchData> {
    @Override
    public BatchData process(BatchData item) {
        // 返回null，会过滤掉这条数据
        return "".equals(item.getField3()) ? null : item;
    }
}
