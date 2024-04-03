package netty.io.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author shenlx
 * @description
 * @date 2024/2/22 9:39
 */
public class TimeServer {
    public void bind(int port) throws InterruptedException {
        /**
         * 配置服务端的NIO线程
         * 创建两个线程组 boosGroup、workerGroup
         */
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        
        try{
            /**创建服务端的启动对象，设置参数*/
            ServerBootstrap serverBootstrap= new ServerBootstrap();
            /**设置两个线程组boosGroup和workerGroup*/
            serverBootstrap.group(bossGroup,workerGroup)
                    /**设置线程队列得到连接个数*/
                    .channel(NioServerSocketChannel.class)
                    /**设置线程队列得到连接个数*/
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childHandler(new ChildChannelHandler());

            /**
             * 绑定端口，同步等待成功
             */
            ChannelFuture sync = serverBootstrap.bind(port).sync();
            /**等待服务端监听端口关闭*/
            sync.channel().closeFuture().sync();
        }finally {
            //优雅退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new TimeServerHandler());
        }
    }

    public static void main(String[]args) throws InterruptedException {
        new TimeServer().bind(8080);
    }
}
