package com.shenlx.xinwen.springbootjdbctemplate.controller;

import com.shenlx.xinwen.springbootjdbctemplate.domain.User;
import com.shenlx.xinwen.springbootjdbctemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-10 14:56
 **/
@RestController
public class UserController {
    @Autowired
    private UserService userService;

        @RequestMapping(value = "/queryuser", method = RequestMethod.GET)
        public User queryuserBySno(String sno) {
            return this.userService.queryBySno(sno);
        }
    
        @RequestMapping(value = "/queryalluser")
        public List<Map<String, Object>> queryAlluser() {
            return this.userService.queryMap();
        }
    
        @RequestMapping(value = "/adduser", method = RequestMethod.GET)
        public int saveuser(String sno,String name,String sex) {
            User user = new User();
            user.setSno(sno);
            user.setName(name);
            user.setSex(sex);
            return this.userService.add(user);
        }
    
        @RequestMapping(value = "deleteuser", method = RequestMethod.GET)
        public int deleteuserBySno(String sno) {
            return this.userService.deleteBysno(sno);
        }
}
