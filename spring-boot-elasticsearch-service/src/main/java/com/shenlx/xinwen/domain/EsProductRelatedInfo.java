package com.shenlx.xinwen.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @program: my-spring-all-service
 * @description: 搜索商品的关联信息，包括品牌名称，分类名称及属性
 * @author: shenlx
 * @create: 2022-12-28 20:07
 **/
@Data
@EqualsAndHashCode
public class EsProductRelatedInfo {
    private List<String> brandNames;
    private List<String> productCategoryNames;
    private List<ProductAttr> productAttrs;

    @Data
    @EqualsAndHashCode
    public static class ProductAttr {
        private Long attrId;
        private String attrName;
        private List<String> attrValues;
    }
}
