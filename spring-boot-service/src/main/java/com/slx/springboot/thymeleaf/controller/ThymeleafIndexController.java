package com.slx.springboot.thymeleaf.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-10 21:19
 **/
@Controller
@Slf4j
public class ThymeleafIndexController {
    /**
     * 主页
     *
     * @param model
     * @return
     */
    @RequestMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("msg", "welcome you!");
        return "index";
    }
}
