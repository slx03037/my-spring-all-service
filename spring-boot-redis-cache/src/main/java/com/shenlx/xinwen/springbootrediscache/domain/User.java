package com.shenlx.xinwen.springbootrediscache.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-14 16:57
 **/
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -7081968807774371394L;
    private Integer id;

    private String username;

    private String password;
}
