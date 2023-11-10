package com.shenlx.xinwen.springbootelasticsearchrestclient.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author shenlx
 * @version 1.0.0
 * @date 2023-11-07 15:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    /**
     * 商品编号
     */
    private Long id;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 商品库存
     */
    private Integer stock;

    /**
     * 商品销售数量
     */
    private Integer saleNum;

    /**
     * 商品分类
     */
    private String categoryName;

    /**
     * 商品品牌
     */
    private String brandName;

    /**
     * 上下架状态
     */
    private Integer status;

    /**
     * 商品创建时间
     */
    private Date createTime;
}
