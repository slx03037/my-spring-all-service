package netty.serial.perform;

import netty.serial.UserInfo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

/**
 * @author shenlx
 * @description
 * @date 2024/2/26 13:40
 */
public class PerformTestUserInfo {
    public static void main(String[]args) throws IOException {
        UserInfo userInfo = new UserInfo();
        userInfo.buildUserID(100).buildUserName("Welcome to Netty");
        int loop=100000;
        ByteArrayOutputStream bos=null;
        ObjectOutputStream os=null;
        long startTime = System.currentTimeMillis();
        for(int i=0; i<loop ; i++){
            bos=new ByteArrayOutputStream();
            os=new ObjectOutputStream(bos);
            os.writeObject(userInfo);
            os.flush();
            os.close();
            byte[] b = bos.toByteArray();
            bos.close();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("The jdk serializable cost time is:" +(endTime -startTime)+"ms");

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        startTime=System.currentTimeMillis();
        for(int i=0; i<loop;i++){
            byte[] bytes = userInfo.codeC(buffer);
        }
        endTime=System.currentTimeMillis();
        System.out.println("The byte array serializable cost time is :"+(endTime-startTime) +"ms");
    }
}
