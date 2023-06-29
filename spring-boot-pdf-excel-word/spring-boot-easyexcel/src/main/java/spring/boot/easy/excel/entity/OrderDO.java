package spring.boot.easy.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;
import spring.boot.easy.excel.aspect.ExcelMerge;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-06-29 15:35
 **/
@Data
public class OrderDO {
    private String id;

    private String orderId;

    private String address;

    private Date createTime;

    private String productId;

    private String name;

    private String subtitle;

    private String brandName;

    private BigDecimal price;

    private Integer count;
}
