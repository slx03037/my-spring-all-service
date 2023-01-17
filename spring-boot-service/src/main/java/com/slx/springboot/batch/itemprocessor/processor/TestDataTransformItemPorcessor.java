package com.slx.springboot.batch.itemprocessor.processor;

import com.slx.springboot.common.entity.BatchData;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-17 10:26
 **/
@Component
public class TestDataTransformItemPorcessor implements ItemProcessor<BatchData, BatchData> {
    @Override
    public BatchData process(BatchData item) throws Exception {
        // field1值拼接 hello
        item.setField1(item.getField1() + " hello");
        return item;
    }
}
