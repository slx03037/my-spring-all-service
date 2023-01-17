package com.slx.springboot.batch.itemwrite.reader;

import com.slx.springboot.common.entity.BatchData;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-17 10:22
 **/
@Configuration
public class ItemReaderConfigure {
    @Bean
    public ListItemReader<BatchData> simpleReader() {
        List<BatchData> data = new ArrayList<>();
        BatchData BatchData1 = new BatchData();
        BatchData1.setId(1);
        BatchData1.setField1("11");
        BatchData1.setField2("12");
        BatchData1.setField3("13");
        data.add(BatchData1);
        BatchData BatchData2 = new BatchData();
        BatchData2.setId(2);
        BatchData2.setField1("21");
        BatchData2.setField2("22");
        BatchData2.setField3("23");
        data.add(BatchData2);
        return new ListItemReader<>(data);
    }
}
