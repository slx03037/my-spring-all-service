package netty.messagepack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * @author shenlx
 * @description
 * @date 2024/2/26 17:01
 */
public class MsgpackDecoder extends MessageToMessageDecoder<ByteBuf> {
    /***
     * 首先从数据报恩arg1中获取需要解码的byte数组，然后调用MessagePack的read方法将其反序列化位Object对象，将解码后的对加入到解码列表out中
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        final  byte[] array;

        final int length = msg.readableBytes();

        array = new byte[length];

        msg.getBytes(msg.readerIndex(),array,0,length);

        MessagePack messagePack = new MessagePack();

        out.add(messagePack.read(array));
    }
}
