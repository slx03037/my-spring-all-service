package netty.tcp.Unpacking.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author shenlx
 * @description
 * @date 2024/2/26 11:21
 */
@ChannelHandler.Sharable
public class EchoFixedSeverHandler extends ChannelInboundHandlerAdapter {

    public EchoFixedSeverHandler(){

    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       System.out.println("Receive client:[" + msg +"]");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
    }

}
