package com.shenlx.xinwen.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-04-26 09:32
 **/
//@Deprecated //注解表示该类或者方法已经过失
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1086167838126056506L;
    private Integer id;
    private String username;
    private String password;
    private String status;
    private Date createTime;
}
