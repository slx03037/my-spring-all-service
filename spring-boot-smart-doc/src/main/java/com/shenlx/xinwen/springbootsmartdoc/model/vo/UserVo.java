package com.shenlx.xinwen.springbootsmartdoc.model.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-07-11 15:49
 **/

@Data
@Builder
public class UserVo {

    /**
     * username.
     */
    private String name;

    /**
     * user's age.
     */
    private int age;

    /**
     * user's address.
     */
    private AddressVo address;
}
