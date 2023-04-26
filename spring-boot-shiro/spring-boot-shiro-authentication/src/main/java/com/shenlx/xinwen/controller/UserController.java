package com.shenlx.xinwen.controller;

import com.shenlx.xinwen.domain.User;
import com.shenlx.xinwen.response.ResponseBo;
import com.shenlx.xinwen.util.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-04-26 09:35
 **/
@Controller
@Slf4j
public class UserController {
    @GetMapping("/hello")
    public String getHello(Model model){

        return "hello";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseBo login(String username, String password) {
        log.info("login==username，{}---password：{}",username,password);
        //密码加密
        password = MD5Utils.encrypt(username, password);
        //生成token
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 获取当前用户
        Subject subject = SecurityUtils.getSubject();
        try {
            // 执行登录方法
            subject.login(token);
            return ResponseBo.ok();
        } catch (UnknownAccountException e) {
            //用户名不存在
            return ResponseBo.error(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            // 密码不存在
            return ResponseBo.error(e.getMessage());
        } catch (LockedAccountException e) {
            //用户被锁定
            return ResponseBo.error(e.getMessage());
        } catch (AuthenticationException e) {
            //
            return ResponseBo.error("认证失败！");
        }
    }

    @RequestMapping("/")
    public String redirectIndex() {
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index(Model model) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("user", user);
        return "index";
    }
}
