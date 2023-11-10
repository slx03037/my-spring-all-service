package com.shenlx.xinwen.springbootelasticsearchrestclient.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shenlx
 * @version 1.0.0
 * @date 2023-11-08 15:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String name;
    private Integer age;
    private String sex;

}
