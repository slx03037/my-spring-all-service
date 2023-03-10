package com.shenlx.xinwen.springbootjdbctemplatemultidatasource.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-10 16:09
 **/
@Configuration
public class DataSourceConfig {
    @Primary
    @Bean(name = "mysqld01atasource")
    //@ConfigurationProperties("spring.datasource.devdb") //配置文件对应druid01
    @ConfigurationProperties("spring.datasource.druid.devdb")
    public DataSource dataSourceOne(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "mysqld02atasource")
    //@ConfigurationProperties("spring.datasource.druid.proddb") //配置文件对应druid01
    @ConfigurationProperties("spring.datasource.druid.proddb")
    public DataSource dataSourceTwo(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "mysql01JdbcTemplate")
    public JdbcTemplate primaryJdbcTemplate(
            @Qualifier("mysqld01atasource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "mysql02JdbcTemplate")
    public JdbcTemplate secondaryJdbcTemplate(
            @Qualifier("mysqld02atasource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
