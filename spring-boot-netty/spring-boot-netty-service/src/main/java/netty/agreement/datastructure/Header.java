package netty.agreement.datastructure;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @author shenlx
 * @description
 * @date 2024/3/28 15:29
 */
public class Header {

    /**固定值，表名这是Netty消息 4字节*/
    private int crcCode=0xabef0101;

    /**消息长度*/
    private int length;

    /**会话ID*/
    private long sessionID;

    /**消息类型*/
    /**
     * 0:业务请求消息;
     * 1:业务响应消息;
     * 2:业务ONE WAY 消息（既是请求又是响应消息);
     * 3:握手请求消息;
     * 4:握手应答消息;
     * 5:心跳请求消息;
     * 6:心跳应答消息。
     */
    private byte type;

    /**消息优先级*/
    private byte priority;

    /**附件/扩展消息头*/
    private Map<String,Object> attachment=new HashMap<String,Object>();

    public int getCrcCode() {
        return crcCode;
    }

    public void setCrcCode(int crcCode) {
        this.crcCode = crcCode;
    }

    public final int getLength() {
        return length;
    }

    public final void setLength(int length) {
        this.length = length;
    }

    public final long getSessionID() {
        return sessionID;
    }

    public final void setSessionID(long sessionID) {
        this.sessionID = sessionID;
    }

    public final byte getType() {
        return type;
    }

    public final void setType(byte type) {
        this.type = type;
    }

    public final byte getPriority() {
        return priority;
    }

    public final void setPriority(byte priority) {
        this.priority = priority;
    }

    public final Map<String, Object> getAttachment() {
        return attachment;
    }

    public final void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Header.class.getSimpleName() + "[", "]")
                .add("crcCode=" + crcCode)
                .add("length=" + length)
                .add("sessionID=" + sessionID)
                .add("type=" + type)
                .add("priority=" + priority)
                .add("attachment=" + attachment)
                .toString();
    }
}
