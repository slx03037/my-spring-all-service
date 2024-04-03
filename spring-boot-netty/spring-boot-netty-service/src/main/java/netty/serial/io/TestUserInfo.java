package netty.serial.io;

import netty.serial.UserInfo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author shenlx
 * @description
 * @date 2024/2/26 13:31
 */
public class TestUserInfo {
    public static void main(String[]args) throws IOException {
        UserInfo userInfo = new UserInfo();
        userInfo.buildUserID(100).buildUserName("Welconme to Netty");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(userInfo);
        os.flush();
        os.close();
        byte[] b = bos.toByteArray();
        System.out.println("The jdk serializable length is :"+b.length);
        bos.close();
        System.out.println("The byte array serializable lenght is:"+userInfo.codeC().length);
    }
}
