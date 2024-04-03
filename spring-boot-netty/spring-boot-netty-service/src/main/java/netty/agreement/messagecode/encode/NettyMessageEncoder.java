package netty.agreement.messagecode.encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import netty.agreement.datastructure.Header;
import netty.agreement.datastructure.NettyMessage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author shenlx
 * @description
 * @date 2024/3/28 15:36
 */
public class NettyMessageEncoder extends MessageToByteEncoder<NettyMessage> {
    MarshallingEncoder marshallingEncoder;

    public NettyMessageEncoder() throws IOException {
        this.marshallingEncoder = new MarshallingEncoder();
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, ByteBuf sendBuf) throws Exception {
        if (null == msg || null == msg.getHeader()) {
            /**编码失败,没有数据信息*/
            throw new Exception("The encode message is null");
        }
        //Head:
        Header header = msg.getHeader();
        //---写入crcCode---
        sendBuf.writeInt(header.getCrcCode());
        //---写入length---
        sendBuf.writeInt(header.getLength());
        //---写入sessionId---
        sendBuf.writeLong(header.getSessionID());
        //---写入type---
        sendBuf.writeByte(header.getType());
        //---写入priority---
        sendBuf.writeByte(header.getPriority());
        //对附件信息进行编码
        //编码规则为：如果attachment的长度为0，表示没有可选附件，则将长度    编码设置为0
        //如果attachment长度大于0，则需要编码，规则：
        //首先对附件的个数进行编码
        //---写入附件大小---
        sendBuf.writeInt((msg.getHeader().getAttachment().size()));//附件大小

        String key = null;
        byte[] keyArray = null;
        Object value = null;

        //然后对key进行编码，先编码长度，然后再将它转化为byte数组之后编码内容
        for (Map.Entry<String, Object> param : msg.getHeader().getAttachment()
                .entrySet()) {
            key = param.getKey();
            keyArray = key.getBytes(StandardCharsets.UTF_8);

            //key的字符编码长度
            sendBuf.writeInt(keyArray.length);
            sendBuf.writeBytes(keyArray);
            value = param.getValue();
            // marshallingEncoder.encode(value, sendBuf);
        }
        // for gc
        key = null;
        keyArray = null;
        value = null;

        //Body:
        //Object body = message.getBody();
        //如果不为空 说明: 有数据
        if (msg.getBody() != null) {
            //使用MarshallingEncoder
            marshallingEncoder.encode(msg.getBody(), sendBuf);
        } else {
            //如果没有数据 则进行补位 为了方便后续的 decoder操作
            sendBuf.writeInt(0);
        }
        //最后我们要获取整个数据包的总长度 也就是 header +  body 进行对 header length的设置

        // TODO:  解释： 在这里必须要-8个字节 ，是因为要把CRC和长度本身占的减掉了
        //（官方中给出的是：LengthFieldBasedFrameDecoder中的lengthFieldOffset+lengthFieldLength）
        //总长度是在header协议的第二个标记字段中
        //第一个参数是长度属性的索引位置
        // 之前写了crcCode 4bytes，除去crcCode和length 8bytes即为更新之后的字节
        sendBuf.setInt(4, sendBuf.readableBytes() - 8);
    }
}
