package netty.messagepack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * @author shenlx
 * @description
 * @date 2024/2/26 16:44
 */
public class MsgpackEncoder extends MessageToByteEncoder<Object> {
    /**
     * encode它负责将Object类型的POJO对象编码位byte数组，然后写入到ByteBuf中
     */

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        MessagePack messagePack = new MessagePack();

        byte[] write = messagePack.write(msg);

        out.writeBytes(write);
    }
}
