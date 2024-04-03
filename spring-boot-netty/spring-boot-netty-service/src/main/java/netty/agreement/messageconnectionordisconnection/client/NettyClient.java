package netty.agreement.messageconnectionordisconnection.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

import netty.agreement.exmaple.server.NettyConstant;
import netty.agreement.heartbeat.req.HeartBeatReqHandler;
import netty.agreement.loginAuth.client.LoginAuthReqHandler;
import netty.agreement.messagecode.decode.NettyMessageDecoder;
import netty.agreement.messagecode.encode.NettyMessageEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.*;

/**
 * @author shenlx
 * @description
 * @date 2024/4/1 22:33
 */
public class NettyClient {
    private static final int CORE_POOL_SIZE=5;//核心线程数为
    private static final int  MAX_POOL_SIZE=10; //最大线程数
    private static final Long KEEP_ALIVE_TIME=2L;//等待时间为 1L
    private static final int QUEUE_CAPACITY=100;//任务队列为 ArrayBlockingQueue，并且容量为 100
    ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(
            CORE_POOL_SIZE
            ,MAX_POOL_SIZE
            ,KEEP_ALIVE_TIME
            , TimeUnit.SECONDS
            ,new ArrayBlockingQueue<>(QUEUE_CAPACITY)
            ,new ThreadPoolExecutor.CallerRunsPolicy()
    );
    /**
     * 废弃不用 容易造成内存溢出
     */
    private final ScheduledExecutorService executor= Executors.newScheduledThreadPool(1);

    EventLoopGroup group=new NioEventLoopGroup();

    public void connect(int port,String host) throws InterruptedException {
        //配置客户端NIO线程组
        try{
            Bootstrap b=new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new NettyMessageDecoder(1024*1024,4,4));
                            ch.pipeline().addLast("MessageEncoder",new NettyMessageEncoder());
                            ch.pipeline().addLast("readTimeoutHandler",new ReadTimeoutHandler(50));
                            ch.pipeline().addLast("LoginAuthHandler",new LoginAuthReqHandler());
                            ch.pipeline().addLast("HeartBeatHandler",new HeartBeatReqHandler());
                        }
                    });
            //发起异步连接操作
            ChannelFuture future = b.connect(new InetSocketAddress(host, port)
                    , new InetSocketAddress(NettyConstant.LOCALIP, NettyConstant.LOCAL_PORT)).sync();
            future.channel().closeFuture().sync();
        }finally {
            //所有资源释放完成之后，清空资源，再发起重连操作
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try{
                        TimeUnit.SECONDS.sleep(5);
                        try{
                            //发起重连操作
                            connect(NettyConstant.PORT,NettyConstant.REMOTEIP);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

        }
    }

    public static void main(String[]args) throws InterruptedException {
        new NettyClient().connect(NettyConstant.PORT,NettyConstant.REMOTEIP);
    }
}
