package com.shenlx.xinwen.springbootredis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shenlx.xinwen.springbootredis.domain.User;
import org.springframework.stereotype.Repository;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-15 21:56
 **/
@Repository
public interface UserMapper extends BaseMapper<User> {
}
