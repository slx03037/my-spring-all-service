package netty.agreement.messageconnectionordisconnection.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import netty.agreement.exmaple.client.LoginAuthReqHandler;
import netty.agreement.exmaple.server.NettyConstant;
import netty.agreement.heartbeat.resp.HeartBeatRespHandler;
import netty.agreement.loginAuth.server.LoginAuthRespHandler;
import netty.agreement.messagecode.decode.NettyMessageDecoder;
import netty.agreement.messagecode.encode.NettyMessageEncoder;

/**
 * @author shenlx
 * @description
 * @date 2024/4/1 22:34
 */
@Slf4j
public class NettyServer {
    public  void bind()throws Exception{
        //配置服务单的NIO线程组
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workGroup=new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();

        b.group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,100)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new NettyMessageDecoder(1024*1024,4,4));
                        ch.pipeline().addLast(new NettyMessageEncoder());
                        ch.pipeline().addLast("readTimeoutHandler",new ReadTimeoutHandler(50));
                        ch.pipeline().addLast(new LoginAuthRespHandler());
                        ch.pipeline().addLast("HearBeatHandler",new HeartBeatRespHandler());

                    }
                });
        //绑定端口，同步等待成功
        b.bind(NettyConstant.REMOTEIP, NettyConstant.PORT).sync();
        log.info("Netty server start ok:[{}:{}]",NettyConstant.REMOTEIP,NettyConstant.PORT);
    }

    public static void main(String[]args) throws Exception {
        new NettyServer().bind();
    }

}
