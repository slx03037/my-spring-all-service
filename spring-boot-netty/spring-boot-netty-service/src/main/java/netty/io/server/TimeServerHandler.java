package netty.io.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author shenlx
 * @description
 * @date 2024/2/22 10:06
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /**
         * 将msg转换成Netty的ByteBuf对象，ByteBuf类似于JDK中的nio.ByteBuffer对象，不过它提供了更加强大和灵活的功能，通过ByteBuf的
         * readableBytes方法可以获取缓冲去可读的字节数，根据可读的字节数创建byte数组，通过ByteBuf的readBytes方法将缓冲区中的字节数组复制到心的byte数组中
         * 最后通过new String构造函数获取请求消息
         */
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        String body = new String(bytes, StandardCharsets.UTF_8);
        System.out.println("The time server receive order:"+body);
        String currentTime="QUERY TIME ORDER".equalsIgnoreCase(body)?new Date(System.currentTimeMillis()).toString():"BAD ORDER";
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.write(resp);
    }

    /**
     * 调用ChannelHandlerContext的flush方法，它的作用是将消息发送队列的消息写入到SocketChannel中发送给对方
     * 为了防止频繁地唤醒Selector进行消息发送，Netty的write方法并不直接将消息写入SocketChannel中，调用
     * write方法只是把带发送的消息发送缓冲数组中，再通过调用flush方法，将发送缓冲区中的消息全部写到SocketChannel中
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * 发送异常时，关闭ChannelHandlerContext，释放和ChannelHandlerContext相关联的句柄等资源
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
    }
}
