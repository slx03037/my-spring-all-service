package com.shenlx.xinwen.springbootjackson.domain;

import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-21 10:26
 **/
//@JsonIgnoreProperties({ "password", "age" })
//@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
//@JsonSerialize(using = UserSerializer.class)
//@JsonDeserialize (using = UserDeserializer.class)
public class User implements Serializable {
    private static final long serialVersionUID = 1644979175672514876L;

    public interface UserNameView {
    };

    public interface AllUserFieldView extends UserNameView {
    };


    @JsonView(UserNameView.class)
    private String userName;

    @JsonView(AllUserFieldView.class)
    private int age;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    // @JsonIgnore
    @JsonView(AllUserFieldView.class)
    private String password;

    // @JsonProperty("bth")
    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(AllUserFieldView.class)
    private Date birthday;
}
