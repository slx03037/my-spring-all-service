package com.shenlx.xinwen.springbootjackson.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shenlx.xinwen.springbootjackson.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-21 10:32
 **/
@RestController
public class JsonController {
    @Autowired
    ObjectMapper mapper;

    @JsonView(User.AllUserFieldView.class)
    @RequestMapping("getuser")
    @ResponseBody
    public User getUser() {
        User user = new User();
        user.setUserName("mrbird");
        user.setAge(26);
        user.setPassword("123456");
        user.setBirthday(new Date());
        return user;
    }

    @RequestMapping("serialization")
    @ResponseBody
    public String serialization() {
        try {
            User user = new User();
            user.setUserName("mrbird");
            user.setBirthday(new Date());
            String str = mapper.writeValueAsString(user);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("readjsonstring")
    @ResponseBody
    public String readJsonString() {
        try {
            String json = "{\"name\":\"mrbird\",\"age\":26}";
            JsonNode node = this.mapper.readTree(json);
            String name = node.get("name").asText();
            int age = node.get("age").asInt();
            return name + " " + age;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("readjsonasobject")
    @ResponseBody
    public String readJsonAsObject() {
        try {
            String json = "{\"userName\":\"mrbird\"}";
            User user = mapper.readValue(json, User.class);
            String name = user.getUserName();
            return name;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("formatobjecttojsonstring")
    @ResponseBody
    public String formatObjectToJsonString() {
        try {
            User user = new User();
            user.setUserName("mrbird");
            user.setAge(26);
            user.setPassword("123456");
            user.setBirthday(new Date());
            String jsonStr = mapper.writeValueAsString(user);
            return jsonStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("updateuser")
    @ResponseBody
    public int updateUser(@RequestBody List<User> list) {
        return list.size();
    }

    @RequestMapping("customize")
    @ResponseBody
    public String customize() throws JsonParseException, JsonMappingException, IOException {
        String jsonStr = "[{\"userName\":\"mrbird\",\"age\":26},{\"userName\":\"scott\",\"age\":27}]";
        JavaType type = mapper.getTypeFactory().constructParametricType(List.class, User.class);
        List<User> list = mapper.readValue(jsonStr, type);
        String msg = "";
        for (User user : list) {
            msg += user.getUserName();
        }
        return msg;
    }
}
