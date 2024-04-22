package netty.agreement.exmaple.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shenlx
 * @description
 * @date 2024/4/3 14:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Head {
    // 1010 1011 1101 1110 0000 0001 0000 0001
    private int crcCode = 0xabef0101; // 固定值，表名这是Netty消息 4字节
    private int length;// 消息长度
    private long sessionID;// 会话ID
    /**
     * 0:业务请求消息;
     * 1:业务响应消息;
     * 2:业务ONE WAY 消息（既是请求又是响应消息);
     * 3:握手请求消息;
     * 4:握手应答消息;
     * 5:心跳请求消息;
     * 6:心跳应答消息。
     */
    private byte type;// 消息类型
    private byte priority;// 消息优先级
    private Map<String, Object> attachment = new HashMap<String, Object>(); // 扩展消息头
}
