package netty.sever;

import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.channel.RecvByteBufAllocator;

import java.net.SocketAddress;

/**
 * @author shenlx
 * @description
 * @date 2024/4/22 14:15
 */
public interface Unsafe {
    /***
     * Netty中Unsafe可不是JDK原生的Unsafe哦，主要就是一些直接跟IO底层直接相关的通用操作：
     */
    // 接受数据的时候用于分配字节缓冲区的处理器
    RecvByteBufAllocator.Handle recvBufAllocHandle();

    // 本地地址
    SocketAddress localAddress();

    // 远程地址
    SocketAddress remoteAddress();

    //向事件循环注册通道，完成后回调
    void register(EventLoop eventLoop, ChannelPromise promise);

    // 绑定本地地址，完成后回调
    void bind(SocketAddress localAddress, ChannelPromise promise);

    // 连接
    void connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise);

    // 断开连接，完成回调
    void disconnect(ChannelPromise promise);

    // 关闭连接，完成回调
    void close(ChannelPromise promise);

    // 立即关闭，不触发任何事件
    void closeForcibly();

    // 注销，完成回调
    void deregister(ChannelPromise promise);

    // 开始读操作
    void beginRead();

    // 写操作
    void write(Object msg, ChannelPromise promise);

    // 冲刷所有的出站数据
    void flush();

    // 特殊的占位符，不接受通知
    ChannelPromise voidPromise();

    //写操作的出站缓冲区
    ChannelOutboundBuffer outboundBuffer();
}
