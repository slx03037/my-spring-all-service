package com.slx.springboot.common.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-10 16:55
 **/
@Table(name = "T_USER")
@Data
public class UserDO implements Serializable {
    private static final long serialVersionUID = 353631838803805336L;
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWD")
    private String passwd;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "STATUS")
    private String status;
}
