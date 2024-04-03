package netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author shenlx
 * @description
 * @date 2024/2/27 16:29
 */
public class HttpFileServer {
    //private static final String DEFAULT_RUL="/src/netty";

    private static final String DEFAULT_RUL= "spring-boot-netty\\spring-boot-netty-service\\src\\main\\java\\netty";

    public void run(final int port,final String url) throws InterruptedException {
        EventLoopGroup bossgroup = new NioEventLoopGroup();

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap b = new ServerBootstrap();

            b.group(bossgroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            /**向ChannelPipeline中添加HTTP请求消息解码器*/
                            ch.pipeline().addLast("http-decoder",new HttpRequestDecoder());
                            /**
                             * 添加了HttpObjectAggregator解码器，它的作用是将多个消息转换为单一的FullHttpRequest或者FullHttpResponse
                             * ，原因是HTTP解码器在每个HTTP消息中会生成多个消息对象
                             * (1)    HttpRequestIHttpResponse;
                             * (2)    HttpContent;
                             * (3)   LastHttpContent
                             */
                            ch.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65536));
                            /**新增HTTP响应编码器，对HTTP响应消息进行编码；*/
                            ch.pipeline().addLast("http-encoder",new HttpResponseEncoder());
                            /**
                             * 新增Chunkedhandler，它的主要作用是支持异步发送大的码流〈例如大的文件传输）
                             * ，但不占用过多的内存，防止发生Java内存溢出错误。
                             */
                            ch.pipeline().addLast("http-chunked",new ChunkedWriteHandler());
                            /**添加I-IttpFileServerHandIer，用于文件服务器的业务逻辑处理*/
                            ch.pipeline().addLast("fileSeverHandler",new HttpFileServerHandler());
                        }
                    });
            ChannelFuture sync = b.bind(port).sync();
            System.out.println("HTTP 文件目录服务器启动，网址是:" + "http://127.0.0.1:"+port+url);

            sync.channel().closeFuture().sync();
        }finally {
            bossgroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[]args) throws InterruptedException {
        new HttpFileServer().run(8080,DEFAULT_RUL);
    }
}
