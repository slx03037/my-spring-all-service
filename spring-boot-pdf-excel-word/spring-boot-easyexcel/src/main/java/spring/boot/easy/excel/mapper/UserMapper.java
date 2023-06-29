package spring.boot.easy.excel.mapper;

import spring.boot.easy.excel.entity.UserDO;

import java.util.List;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-06-29 14:31
 **/

public interface UserMapper {
    List<UserDO>  excelUserList();
}
