package com.shenlx.xinwen.springbootsmartdoc.controller;

import com.shenlx.xinwen.springbootsmartdoc.model.entity.ResponseResult;
import com.shenlx.xinwen.springbootsmartdoc.model.query.AddressParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-07-11 15:51
 **/

@RestController
@RequestMapping("/address")
public class AddressController {
    /**
     * Add a new address.
     *
     * @param addressParam param
     * @return address
     */
    @PostMapping("add")
    public ResponseResult<String> add(AddressParam addressParam) {
        return ResponseResult.success("success");
    }

}
