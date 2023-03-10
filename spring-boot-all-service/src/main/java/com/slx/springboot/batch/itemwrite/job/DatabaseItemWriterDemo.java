package com.slx.springboot.batch.itemwrite.job;

import com.slx.springboot.common.entity.BatchData;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-16 20:12
 **/

public class DatabaseItemWriterDemo {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private ListItemReader<BatchData> simpleReader;
    @Autowired
    private DataSource dataSource;

    @Bean
    public Job datasourceItemWriterJob() {
        return jobBuilderFactory.get("datasourceItemWriterJob")
                .start(step())
                .build();
    }

    private Step step() {
        return stepBuilderFactory.get("step")
                .<BatchData, BatchData>chunk(2)
                .reader(simpleReader)
                .writer(dataSourceItemWriter())
                .build();
    }

    private ItemWriter<BatchData> dataSourceItemWriter() {
        // ItemWriter的实现类之一，mysql数据库数据写入使用JdbcBatchItemWriter，
        // 其他实现：MongoItemWriter,Neo4jItemWriter等
        JdbcBatchItemWriter<BatchData> writer = new JdbcBatchItemWriter<>();
        writer.setDataSource(dataSource); // 设置数据源

        String sql = "insert into TEST(id,field1,field2,field3) values (:id,:field1,:field2,:field3)";
        writer.setSql(sql); // 设置插入sql脚本

        // 映射BatchData对象属性到占位符中的属性
        BeanPropertyItemSqlParameterSourceProvider<BatchData> provider = new BeanPropertyItemSqlParameterSourceProvider<>();
        writer.setItemSqlParameterSourceProvider(provider);

        writer.afterPropertiesSet(); // 设置一些额外属性
        return writer;
    }
}
