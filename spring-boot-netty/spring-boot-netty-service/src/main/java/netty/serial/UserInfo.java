package netty.serial;

import io.netty.buffer.ByteBuf;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * @author shenlx
 * @description
 * @date 2024/2/26 13:25
 */
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -4578326254411545292L;

    private String userName;

    private int userID;

    public UserInfo buildUserName(String userName){
        this.userName=userName;
        return this;
    }

    public UserInfo buildUserID(int userID){
        this.userID=userID;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public byte[] codeC(){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte[] value = this.userName.getBytes();
        buffer.putInt(value.length);
        buffer.put(value);
        buffer.putInt(this.userID);
        buffer.flip();
        value=null;
        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);
        return result;
    }

    public byte[] codeC(ByteBuffer buffer){
        //ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.clear();
        byte[] value = this.userName.getBytes();
        buffer.putInt(value.length);
        buffer.put(value);
        buffer.putInt(this.userID);
        buffer.flip();
        value=null;
        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);
        return result;
    }
}
