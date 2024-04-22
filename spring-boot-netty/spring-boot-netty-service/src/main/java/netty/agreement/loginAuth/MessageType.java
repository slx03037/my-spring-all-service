package netty.agreement.loginAuth;

/**
 * @author shenlx
 * @description
 * @date 2024/4/1 15:56
 */
public enum MessageType {
    LOGIN_RESP((byte)0,"登录响应")
    ,LOGIN_REQ((byte)0,"登录认证")

    ,LOGIN_RESPONSE((byte)1,"认证成功")
    ,HEARTBEAT_REQ((byte)2,"业务请求")
    ,HEARTBEAT_RESP((byte)3,"业务响应")
    ;
    private final Byte value;
    private final String name;
    MessageType(Byte value,String name){
        this.name = name;
        this.value = value;
    }

    public Byte value() {
        return value;
    }
    public Byte getValue() {
        return value;
    }
    public String getName(){
        return name;
    }
}
