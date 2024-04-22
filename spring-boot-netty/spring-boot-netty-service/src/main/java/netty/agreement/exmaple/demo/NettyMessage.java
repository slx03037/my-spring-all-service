package netty.agreement.exmaple.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shenlx
 * @description
 * @date 2024/4/3 14:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NettyMessage {
    private Head header; // 头部
    private Object body; // 消息正文
}
