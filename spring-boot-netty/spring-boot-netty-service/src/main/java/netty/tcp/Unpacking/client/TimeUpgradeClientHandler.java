package netty.tcp.Unpacking.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author shenlx
 * @description
 * @date 2024/2/26 9:29
 */
public class TimeUpgradeClientHandler extends ChannelInboundHandlerAdapter {
    private int counter;

    private final byte[] req;

    public  TimeUpgradeClientHandler(){
        req=("QUERY TIME ORDER"+System.getProperty("line.separator")).getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message=null;

        for(int i=0; i<100; i++){
            message= Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body=(String)msg;

        System.out.println("Now is :"+body+"; the counter is:"+ ++counter);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
       ctx.close();
    }
}
