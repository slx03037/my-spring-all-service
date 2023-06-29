package spring.boot.easy.excel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.boot.easy.excel.entity.UserDO;
import spring.boot.easy.excel.mapper.UserMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-06-29 15:03
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserMapper userMapper;
    @GetMapping("/get/user")
    public List<UserDO> getUser() {
        return userMapper.excelUserList();
    }
}
