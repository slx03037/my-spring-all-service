package com.slx.springboot.batch.itemwrite.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.slx.springboot.common.entity.BatchData;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-16 20:14
 **/
@Component
public class FileItemWriterDemo {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private ListItemReader<BatchData> simpleReader;

    @Bean
    public Job fileItemWriterJob() throws Exception {
        return jobBuilderFactory.get("fileItemWriterJob")
                .start(step())
                .build();
    }

    private Step step() throws Exception {
        return stepBuilderFactory.get("step")
                .<BatchData, BatchData>chunk(2)
                .reader(simpleReader)
                .writer(fileItemWriter())
                .build();
    }

    private FlatFileItemWriter<BatchData> fileItemWriter() throws Exception {
        FlatFileItemWriter<BatchData> writer = new FlatFileItemWriter<>();

        FileSystemResource file = new FileSystemResource("/Users/mrbird/Desktop/file");
        Path path = Paths.get(file.getPath());
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        writer.setResource(file); // 设置目标文件路径

        // 把读到的每个BatchData对象转换为JSON字符串
        LineAggregator<BatchData> aggregator = item -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.writeValueAsString(item);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return "";
        };

        writer.setLineAggregator(aggregator);
        writer.afterPropertiesSet();
        return writer;
    }
}
