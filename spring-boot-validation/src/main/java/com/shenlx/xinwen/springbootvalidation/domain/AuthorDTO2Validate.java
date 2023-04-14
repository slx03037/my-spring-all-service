package com.shenlx.xinwen.springbootvalidation.domain;

import com.shenlx.xinwen.springbootvalidation.aspect.Validate;
import lombok.Data;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-04-14 16:42
 **/
@Data
public class AuthorDTO2Validate {
    @Validate(values = {1,2},message = "性别只能传入1或者2")
    private Integer gender;
}
