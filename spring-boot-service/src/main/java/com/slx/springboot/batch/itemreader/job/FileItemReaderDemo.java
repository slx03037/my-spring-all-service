package com.slx.springboot.batch.itemreader.job;

import com.slx.springboot.common.entity.BatchData;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-16 19:01
 **/
@Component
public class FileItemReaderDemo {
    // 任务创建工厂
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    // 步骤创建工厂
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job fileItemReaderJob() {
        return jobBuilderFactory.get("fileItemReaderJob")
                .start(step())
                .build();
    }

    private Step step() {
        return stepBuilderFactory.get("step")
                .<BatchData, BatchData>chunk(2)
                .reader(fileItemReader())
                .writer(list -> list.forEach(System.out::println))
                .build();
    }

    private ItemReader<BatchData> fileItemReader() {
        FlatFileItemReader<BatchData> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("file")); // 设置文件资源地址
        reader.setLinesToSkip(1); // 忽略第一行

        // AbstractLineTokenizer的三个实现类之一，以固定分隔符处理行数据读取,
        // 使用默认构造器的时候，使用逗号作为分隔符，也可以通过有参构造器来指定分隔符
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();

        // 设置属性名，类似于表头
        tokenizer.setNames("id", "field1", "field2", "field3");
        // 将每行数据转换为BatchData对象
        DefaultLineMapper<BatchData> mapper = new DefaultLineMapper<>();
        mapper.setLineTokenizer(tokenizer);
        // 设置映射方式
        mapper.setFieldSetMapper(fieldSet -> {
            BatchData data = new BatchData();
            data.setId(fieldSet.readInt("id"));
            data.setField1(fieldSet.readString("field1"));
            data.setField2(fieldSet.readString("field2"));
            data.setField3(fieldSet.readString("field3"));
            return data;
        });

        reader.setLineMapper(mapper);
        return reader;
    }
}
