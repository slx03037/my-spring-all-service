package com.shenlx.xinwen.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @program: my-spring-all-service
 * @description: 搜索商品的属性信息
 * @author: shenlx
 * @create: 2022-12-28 20:06
 **/
@Data
@EqualsAndHashCode
public class EsProductAttributeValue implements Serializable {
    private Long id;
    private Long productAttributeId;
    //属性值
    @Field(type = FieldType.Keyword)
    private String value;
    //属性参数：0->规格；1->参数
    private Integer type;
    //属性名称
    @Field(type=FieldType.Keyword)
    private String name;
}
