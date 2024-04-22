package com.shenlx.xinwen.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-04-25 10:28
 **/
@Controller
@RequestMapping("/shiro")
public class UserController {
    @RequestMapping("/login")
    public String login(String username,String password){
        System.out.println(username+":"+password);
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(true);
            try {
                currentUser.login(token);
            }
            catch (AuthenticationException ae) {
                System.out.println("登录失败: " + ae.getMessage());
            }
        }
        return "redirect:/admin.jsp";
    }

    @RequestMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        System.out.println("退出成功");
        return "redirect:/login.jsp";
    }

    /**
     * 编程式就是在代码中使用hasRole或isPermitted等方法来进行权限判断。
     * 除了hashRole，常见的方法还有：
     *  hasRoles(List<String> roleIdentifiers)：拥有List中的所有角色才返回true
     *  hasAllRoles(Collection<String> roleIdentifiers)：拥有集合中的所有角色才返回true
     *  isPermitted(String... permissions)：是否拥有某个行为(支持传入多个参数)
     * @return
     */
    @RequestMapping("/deluser")
    public String deluser(){
        Subject subject = SecurityUtils.getSubject();
        if (subject.hasRole("admin")){
            //一系列操作....
            System.out.println("执行了删除用户的操作");
            return "redirect:/admin.jsp";
        }else{
            System.out.println("你没有权限执行");
            return "redirect:/unauthorized.jsp";
        }
    }
}
