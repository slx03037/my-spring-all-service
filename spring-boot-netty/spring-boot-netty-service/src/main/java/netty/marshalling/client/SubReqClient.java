package netty.marshalling.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.marshalling.MarshallingCodeCfactory;

/**
 * @author shenlx
 * @description
 * @date 2024/2/27 14:27
 */
public class SubReqClient {
    public void connect(int port,String host) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(MarshallingCodeCfactory.buildMarshallingDecoder());
                            ch.pipeline().addLast(MarshallingCodeCfactory.buildMarshallingEncoder());
                            //ch.pipeline().addLast(new SubReqClientHandler());
                            ch.pipeline().addLast(new netty.protobuf.client.SubReqClientHandler());
                        }
                    });
            /**发起异步连接操作*/
            ChannelFuture sync = bootstrap.connect(host, port).sync();

            /**等待客户端链路关闭*/
            sync.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String []args) throws InterruptedException {
        new SubReqClient().connect(8080,"127.0.0.1");
    }
}
