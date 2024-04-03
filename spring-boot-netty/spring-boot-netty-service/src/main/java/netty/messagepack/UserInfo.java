package netty.messagepack;

import org.msgpack.annotation.Message;

import java.io.Serializable;

/**
 * @author shenlx
 * @description
 * @date 2024/2/26 17:37
 */
@Message
public class UserInfo implements Serializable {
    private static final long serialVersionUID = -3528523033546102535L;
    private int age;

    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
