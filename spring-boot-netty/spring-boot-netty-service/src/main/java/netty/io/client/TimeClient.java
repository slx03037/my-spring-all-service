package netty.io.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author shenlx
 * @description
 * @date 2024/2/22 10:39
 */
public class TimeClient {

    public void connect(int port,String host) throws InterruptedException {
        /**
         * 读写的NioEventLoopGroup线程组，然后继续创建客户端辅助启动类Bootstrap，随后需要对其进行配置，与服务端不同的是，它的
         * Channel需要设置NioSocketChannel，然后为其添加Handler，此处为了简单直接创建匿名内部类，实现initChannel方法，
         * 其作用是当创建NioSocketChannel成功之后，再进行初始化时，将它的ChannelHandler设置到ChannelPipeline中，用于处理网络I/O事件
         */
        //配置客户端NIO线程组
        NioEventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new TimeClientHandler());
                        }
                    });
            //发起异步连接操作
            ChannelFuture sync = bootstrap.connect(host, port).sync();
            //等待客户端链路关闭
            sync.channel().closeFuture().sync();
        }finally {
            //优雅退出，释放NIO线程组
            group.shutdownGracefully();
        }
    }

    public static void main(String[]args) throws InterruptedException {
        new TimeClient().connect(8080,"127.0.0.1");
    }
}
