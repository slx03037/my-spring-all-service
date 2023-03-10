package com.slx.springboot.mapper_pagehelper.config;

import com.baomidou.mybatisplus.core.mapper.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-10 16:59
 **/

public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
