package netty.messagepack.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import netty.messagepack.MsgpackDecoder;
import netty.messagepack.MsgpackEncoder;

/**
 * @author shenlx
 * @description
 * @date 2024/2/26 17:29
 */
public class EchoClient {
    private final String host;

    private final int port;

    private final int sendNumber;

    public EchoClient(String host, int port, int sendNumber) {
        this.host = host;
        this.port = port;
        this.sendNumber = sendNumber;
    }

    public void connect() throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();

        try{
            /**创建bootstrap对象，配置其参数*/
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(group)
                    /**设置客户端的通道实现类型*/
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    /**设置的通道连接时间*/
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,3000)
                    /**使用匿名内部类初始化通道*/
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("frameDecoder"
                                    ,new LengthFieldBasedFrameDecoder(65535,0,2,0,2));
                            ch.pipeline().addLast("msgpack decoder",new MsgpackDecoder());
                            ch.pipeline().addLast("frameEncoder",new LengthFieldPrepender(2));
                            ch.pipeline().addLast("msgpack encoder",new MsgpackEncoder());
                            //ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new EchoClientHandler(sendNumber));
                        }
                    });

            /**连接服务端*/
            ChannelFuture f = bootstrap.connect(host,port).sync();

            /**对通过关闭进行监听*/
            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[]args) throws InterruptedException {
        new EchoClient("127.0.0.1",8080,20).connect();
    }
}
