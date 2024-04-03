package netty.marshalling.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import netty.marshalling.RespData;

/**
 * @author shenlx
 * @description
 * @date 2024/2/27 14:52
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 当我们的通道被激活的时候触发的监听
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("--------客户端通道激活---------");
    }

    /**
     * 当我们通道里有数据进行读取的时候触发的监听
     *
     * @param ctx netty服务上下文
     * @param msg 实际传输的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            RespData respData = (RespData)msg;
            System.out.println("客户端：获取服务端响应数据-->"+respData.getId()+","+respData.getName()+","+respData.getAge());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放数据 （如果你读取数据后又写出去数据就不需要调用此方法，因为底层代码帮忙实现额释放数据）
            ReferenceCountUtil.release(msg);
        }
    }

    /**
     * 当我们读取完成数据的时候触发的监听
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("--------客户端数据读取完毕---------");
    }

    /**
     * 当我们读取数据异常的时候触发的监听
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("--------客户端数据读取异常---------");
        ctx.close();
    }
}
