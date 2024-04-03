package netty.tcp.stickypackage.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.Date;


/**
 * @author shenlx
 * @description
 * @date 2024/2/23 11:36
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {
    private int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /**
         * 每读到一条消息后，就记一次数，然后发送应答消息给客户端，按照设计，
         * 服务端接收到消息总数应该跟客户端发送的消息总数相同，
         * 而且请求消息删除回车换行符后应该为“QUERY TIME ORDER”.
         */
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        String body = new String(bytes, StandardCharsets.UTF_8).substring(0,bytes.length
                - System.getProperty("line.separator").length());

        System.out.println("The time server receive order:" + body
                + ";the counter is :"+ ++counter);

        String currentTime="QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(
                System.currentTimeMillis()).toString() : "BAD ORDER";

        currentTime=currentTime + System.getProperty("line.separator");

        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(resp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
       // ctx.fireExceptionCaught(cause);
    }

}
