package com.shenlx.xinwen.springbootmybatismultidatasource.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-12 18:36
 **/
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 3994877935722008763L;
    private Integer id;
    /**
     * 账号
     */
    private String username;
    /**
     * 名字
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * md5密码盐
     */
    private String salt;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 备注
     */
    private String tips;
    /**
     * 状态 1:正常 2:禁用
     */
    private Integer state;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新时间
     */
    private Date updatedTime;
}
