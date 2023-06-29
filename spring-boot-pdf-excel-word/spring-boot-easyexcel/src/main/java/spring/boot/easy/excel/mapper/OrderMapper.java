package spring.boot.easy.excel.mapper;

import spring.boot.easy.excel.entity.OrderBO;
import spring.boot.easy.excel.entity.OrderDO;

import java.util.List;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-06-29 15:38
 **/

public interface OrderMapper {
    List<OrderBO> getOrderlist();
}
