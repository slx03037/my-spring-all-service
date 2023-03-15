package com.shenlx.xinwen.springbootredis.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-15 21:54
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "t_user")
public class User extends Model<User> implements Serializable {
    private static final long serialVersionUID = -498108660634873734L;

    @TableId(value="id", type= IdType.INPUT)
    private Integer id;

    private String username;

    private String password;

}
