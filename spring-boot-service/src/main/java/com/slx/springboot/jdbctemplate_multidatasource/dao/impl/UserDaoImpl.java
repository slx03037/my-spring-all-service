package com.slx.springboot.jdbctemplate_multidatasource.dao.impl;

import com.slx.springboot.jdbctemplate_multidatasource.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-07 21:46
 **/
@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    @Qualifier("mysqlJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> getAllStudents() {
        return this.jdbcTemplate.queryForList("select * from tbl_user_info");
    }
}
