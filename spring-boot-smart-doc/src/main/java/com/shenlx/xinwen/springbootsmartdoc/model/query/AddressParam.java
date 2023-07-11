package com.shenlx.xinwen.springbootsmartdoc.model.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-07-11 15:48
 **/

@Data
@Builder
@AllArgsConstructor
public class AddressParam {

    /**
     * city.
     */
    private String city;

    /**
     * zip code.
     */
    private String zipcode;
}
