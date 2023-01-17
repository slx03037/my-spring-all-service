package com.slx.springboot.batch.itemreader.job;

import com.slx.springboot.common.entity.BatchData;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-16 19:49
 **/
@Component
public class XmlFileItemReaderDemo {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job xmlFileItemReaderJob() {
        return jobBuilderFactory.get("xmlFileItemReaderJob")
                .start(step())
                .build();
    }

    private Step step() {
        return stepBuilderFactory.get("step")
                .<BatchData, BatchData>chunk(2)
                .reader(xmlFileItemReader())
                .writer(list -> list.forEach(System.out::println))
                .build();
    }

    private ItemReader<BatchData> xmlFileItemReader() {
        StaxEventItemReader<BatchData> reader = new StaxEventItemReader<>();
        reader.setResource(new ClassPathResource("file.xml")); // 设置xml文件源
        reader.setFragmentRootElementName("test"); // 指定xml文件的根标签
        // 将xml数据转换为BatchData对象
        XStreamMarshaller marshaller = new XStreamMarshaller();
        // 指定需要转换的目标数据类型
        Map<String, Class<BatchData>> map = new HashMap<>(1);
        map.put("test", BatchData.class);
        marshaller.setAliases(map);

        reader.setUnmarshaller(marshaller);
        return reader;
    }
}
