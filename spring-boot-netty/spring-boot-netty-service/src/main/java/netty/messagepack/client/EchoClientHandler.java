package netty.messagepack.client;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.messagepack.UserInfo;

/**
 * @author shenlx
 * @description
 * @date 2024/2/26 17:34
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {
    private final int sendNumber;

    public EchoClientHandler(int sendNumber) {
        this.sendNumber = sendNumber;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        UserInfo[] userInfos = userInfos();
        for(UserInfo info:userInfos){
            ctx.write(info);
        }
        ctx.flush();
    }

    private UserInfo[] userInfos(){
        UserInfo[] userInfos = new UserInfo[sendNumber];

        UserInfo userInfo=null;

        for(int i=0; i<sendNumber ;i++){
            userInfo=new UserInfo();
            userInfo.setAge(i);
            userInfo.setName("ABCDEFG--->"+i);
            userInfos[i] = userInfo;
        }
        return userInfos;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Client receive the msgpack message:"+ msg);
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
