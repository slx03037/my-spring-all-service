package com.slx.springboot.batch.itemwrite.job;

import com.slx.springboot.common.entity.BatchData;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-16 20:16
 **/
@Component
public class JsonFileItemWriterDemo {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private ListItemReader<BatchData> simpleReader;

    @Bean
    public Job jsonFileItemWriterJob() throws Exception {
        return jobBuilderFactory.get("jsonFileItemWriterJob")
                .start(step())
                .build();
    }

    private Step step() throws Exception {
        return stepBuilderFactory.get("step")
                .<BatchData, BatchData>chunk(2)
                .reader(simpleReader)
                .writer(jsonFileItemWriter())
                .build();
    }

    private JsonFileItemWriter<BatchData> jsonFileItemWriter() throws IOException {
        // 文件输出目标地址
        FileSystemResource file = new FileSystemResource("/Users/mrbird/Desktop/file.json");
        Path path = Paths.get(file.getPath());
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        // 将对象转换为json
        JacksonJsonObjectMarshaller<BatchData> marshaller = new JacksonJsonObjectMarshaller<>();
        JsonFileItemWriter<BatchData> writer = new JsonFileItemWriter<>(file, marshaller);
        // 设置别名
        writer.setName("BatchDatasonFileItemWriter");
        return writer;
    }
}
