package com.shenlx.xinwen.word.model.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-07-10 22:38
 **/

@Builder
@Data
public class User implements BaseEntity {

    private static final long serialVersionUID = 2155315461694084990L;
    /**
     * user id.
     */
    private Long id;

    /**
     * username.
     */
    private String userName;

    /**
     * email.
     */
    private String email;

    /**
     * phoneNumber.
     */
    private long phoneNumber;

    /**
     * description.
     */
    private String description;


}
