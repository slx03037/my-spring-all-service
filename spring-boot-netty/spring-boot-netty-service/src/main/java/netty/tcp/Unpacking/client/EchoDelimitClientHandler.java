package netty.tcp.Unpacking.client;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author shenlx
 * @description
 * @date 2024/2/26 10:36
 */
public class EchoDelimitClientHandler extends ChannelInboundHandlerAdapter {
    private int counter;

    private final String ECHO_REQ="hi,BBB,welcome to Netty.$_";

    public EchoDelimitClientHandler(){

    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for(int i=0;i<20;i++){
            ctx.writeAndFlush(Unpooled.copiedBuffer(ECHO_REQ.getBytes()));
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       System.out.println("This is "+ ++counter + "Times receive server :[" + msg + "]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
       ctx.close();
    }
}
