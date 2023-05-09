package com.shenlx.xinwen.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-04-27 14:47
 **/
@Data
public class UserDO implements Serializable {
    private static final long serialVersionUID = -6270878337608605104L;
    private String username;

    private String password;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked= true;

    private boolean credentialsNonExpired= true;

    private boolean enabled= true;

}
