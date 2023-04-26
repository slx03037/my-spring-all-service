package com.shenlx.xinwen.springbootthymeleaf.controller;

import com.shenlx.xinwen.springbootthymeleaf.bean.Account;
import com.shenlx.xinwen.springbootthymeleaf.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-12 20:44
 **/
@Controller
public class IndexController {
    @GetMapping("/account")
    public String index(Model m) {
        List<Account> list = new ArrayList<Account>();
        list.add(new Account("KangKang", "康康", "e10adc3949ba59abbe56e", "超级管理员", "17777777777"));
        list.add(new Account("Mike", "麦克", "e10adc3949ba59abbe56e", "管理员", "13444444444"));
        list.add(new Account("Jane","简","e10adc3949ba59abbe56e","运维人员","18666666666"));
        list.add(new Account("Maria", "玛利亚", "e10adc3949ba59abbe56e", "清算人员", "19999999999"));
        m.addAttribute("accountList",list);
        return "account";
    }

    @GetMapping(value = "/user/1")
    public String getUserById(Model model) {
        User user1 = new User("Darcy", "password", 24, new Date(), Arrays.asList("Java", "GoLang"));
        User user2 = new User("Chris", "password", 22, new Date(), Arrays.asList("Java", "Web"));
        ArrayList<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        model.addAttribute("userList", userList);
        model.addAttribute("user", user1);
        return "user";
    }
}
