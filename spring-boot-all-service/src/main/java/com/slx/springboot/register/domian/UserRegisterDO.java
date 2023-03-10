package com.slx.springboot.register.domian;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-17 22:06
 **/
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegisterDO {
    private String name;
    private Integer age;
}
