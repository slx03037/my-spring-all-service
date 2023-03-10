package com.slx.springboot.jwt.model;

import com.slx.springboot.jwt.domain.Manager;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-28 23:31
 **/
@Data
public class ManagerInfo extends Manager implements Serializable {
    private static final long serialVersionUID = 2387077570831006108L;
    /**
     * 状态
     */
    private String stateStr;
    /**
     * 所属项目id列表（逗号分隔）
     */
    private String pids;
    /**
     * 所属项目名列表（逗号分隔）
     */
    private String pnames;
    /**
     * 所属项目id列表
     */
    private List<Integer> pidsList;

    /**
     * 一个管理员具有多个角色
     */
    private List<SysRole> roles;// 一个用户具有多个角色

    public ManagerInfo() {
    }

}
