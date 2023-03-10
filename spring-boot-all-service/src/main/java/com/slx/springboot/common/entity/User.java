package com.slx.springboot.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
//import com.baomidou.mybatisplus.activerecord.Model;
//import org.springframework.ui.Model;

import java.util.Date;

/**
 * @program: my-spring-all-service
 * @description: 后台管理用户表
 * @author: shenlx
 * @create: 2023-01-09 11:44
 **/
@TableName(value = "t_user")
@Data
public class User extends Model<User> {
    /**
     * 主键ID
     */
    @TableId(value="id", type= IdType.AUTO)
    private Integer id;
    /**
     * 账号
     */
    private String username;
    /**
     * 名字
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * md5密码盐
     */
    private String salt;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 备注
     */
    private String tips;
    /**
     * 状态 1:正常 2:禁用
     */
    private Integer state;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新时间
     */
    private Date updatedTime;

//    @Override
//    public Model addAttribute(String attributeName, Object attributeValue) {
//        return null;
//    }
//
//    @Override
//    public Model addAttribute(Object attributeValue) {
//        return null;
//    }
//
//    @Override
//    public Model addAllAttributes(Collection<?> attributeValues) {
//        return null;
//    }
//
//    @Override
//    public Model addAllAttributes(Map<String, ?> attributes) {
//        return null;
//    }
//
//    @Override
//    public Model mergeAttributes(Map<String, ?> attributes) {
//        return null;
//    }
//
//    @Override
//    public boolean containsAttribute(String attributeName) {
//        return false;
//    }
//
//    @Override
//    public Object getAttribute(String attributeName) {
//        return null;
//    }
//
//    @Override
//    public Map<String, Object> asMap() {
//        return null;
//    }
}
