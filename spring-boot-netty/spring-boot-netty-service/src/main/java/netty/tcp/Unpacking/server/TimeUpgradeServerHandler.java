package netty.tcp.Unpacking.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * @author shenlx
 * @description
 * @date 2024/2/23 15:46
 */
public class TimeUpgradeServerHandler extends ChannelInboundHandlerAdapter {
    private int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body=(String)msg;
        System.out.println("The time server receive order:"+body
        +";the counter is:"+ ++counter);

        String currentTime="QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString():"BAD ORDER";

        currentTime = currentTime+System.getProperty("line.separator");

        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());

        ctx.writeAndFlush(resp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
    }
}
