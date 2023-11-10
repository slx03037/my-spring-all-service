package com.shenlx.xinwen.springbootelasticsearchrestclient.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author shenlx
 * @version 1.0.0
 * @date 2023-11-07 22:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String sku;
    private String title;
    private Double price;
}
