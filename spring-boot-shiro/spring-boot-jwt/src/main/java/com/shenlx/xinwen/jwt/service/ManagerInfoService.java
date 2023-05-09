package com.shenlx.xinwen.jwt.service;

import com.shenlx.xinwen.model.enetity.ManagerInfo;
import com.shenlx.xinwen.model.enetity.SysRole;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-05-08 14:43
 **/

@Service
public class ManagerInfoService {

    /**
     * 通过名称查找用户
     * 这里我直接写常量，实际生产环境会通过DAO访问数据库
     *
     * @param username
     * @return
     */
    public ManagerInfo findByUsername(String username) {
        ManagerInfo managerInfo = new ManagerInfo();
        managerInfo.setUsername(username);
        managerInfo.setPids("1,2,3");
        managerInfo.setPidsList(Arrays.asList(1, 2, 3));
        managerInfo.setPnames("第1个,第2个");
        managerInfo.setState(1);
        managerInfo.setCreatedTime(new Date());
        managerInfo.setName("系统管理员");
        // 随机数
        managerInfo.setSalt("ef748186673033723bbf4e056f4ec92e");
        managerInfo.setPassword("da9c3061ae5c0973a3d48e4e721cfbad");
        List<SysRole> roles = new ArrayList<>();
        SysRole role = new SysRole();
        role.setId(1);
        role.setName("admin");
        role.setMemo("超级管理员");
        roles.add(role);
        managerInfo.setRoles(roles);
        return managerInfo;
    }

}
