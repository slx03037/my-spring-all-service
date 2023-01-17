package com.shenlx.xinwen.mapper;

import com.shenlx.xinwen.domain.EsProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: my-spring-all-service
 * @description: 搜索商品管理自定义Dao
 * @author: shenlx
 * @create: 2022-12-28 20:09
 **/
@Mapper
@Component
public interface EsProductDao {
    /**
     * 获取指定ID的搜索商品
     */
    List<EsProduct> getAllEsProductList(@Param("id") Long id);
}
