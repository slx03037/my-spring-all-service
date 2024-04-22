//package netty.sever;
//
//import io.netty.channel.*;
//import io.netty.channel.socket.ChannelOutputShutdownEvent;
//import io.netty.channel.socket.ChannelOutputShutdownException;
//import io.netty.util.internal.ObjectUtil;
//import io.netty.util.internal.UnstableApi;
//
//import java.net.SocketAddress;
//import java.nio.channels.ClosedChannelException;
//
///**
// * @author shenlx
// * @description
// * @date 2024/4/22 14:21
// */
//public abstract class AbstractUnsafe implements Channel.Unsafe {
//
//    private volatile EventLoop eventLoop;
//    private volatile boolean registered;
//    //出站字节缓冲区
//    private volatile ChannelOutboundBuffer outboundBuffer = new ChannelOutboundBuffer(AbstractChannel.this);
//    private RecvByteBufAllocator.Handle recvHandle;//接受数据缓冲分配器的处理器
//    private boolean inFlush0;//是否正在缓冲
//    /** true if the channel has never been registered, false otherwise */
//    private boolean neverRegistered = true;//通道没注册过
//
//    private void assertEventLoop() {//断言还没注册，或者当前线程是IO线程
//        assert !registered || eventLoop.inEventLoop();
//    }
//
//    @Override
//    public RecvByteBufAllocator.Handle recvBufAllocHandle() {
//        if (recvHandle == null) {
//            recvHandle = config().getRecvByteBufAllocator().newHandle();
//        }
//        return recvHandle;
//    }
//
//    @Override
//    public final void register(EventLoop eventLoop, final ChannelPromise promise) {
//        ObjectUtil.checkNotNull(eventLoop, "eventLoop");
//        if (isRegistered()) {//是否已经注册人到一个eventLoop
//            promise.setFailure(new IllegalStateException("registered to an event loop already"));
//            return;
//        }
//        if (!isCompatible(eventLoop)) {//是否是NioEventLoop类型
//            promise.setFailure(
//                    new IllegalStateException("incompatible event loop type: " + eventLoop.getClass().getName()));
//            return;
//        }
//
//        AbstractChannel.this.eventLoop = eventLoop;
////只能当前线程是eventLoop的线程才可以注册，防止多线程并发问题，所以即使多线程来操作，也是安全的，会按照一定顺序提交到任务队列里
//        if (eventLoop.inEventLoop()) {
//            register0(promise);
//        } else {//否则就当做任务提交给eventLoop的任务队列
//            try {
//                eventLoop.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        register0(promise);
//                    }
//                });
//            } catch (Throwable t) {
//                logger.warn(
//                        "Force-closing a channel whose registration task was not accepted by an event loop: {}",
//                        AbstractChannel.this, t);
//                closeForcibly();
//                closeFuture.setClosed();
//                safeSetFailure(promise, t);
//            }
//        }
//    }
//
//    private void register0(ChannelPromise promise) {
//        try {
//
//            if (!promise.setUncancellable() || !ensureOpen(promise)) {//确保是不可取消和通道打开着，否则就返回
//                return;
//            }
//            boolean firstRegistration = neverRegistered;//设置注册标记
//            doRegister();//进行注册逻辑
//            neverRegistered = false;//AbstractUnsafe的已注册标记
//            registered = true;//channel的已注册标记
//
//
//            pipeline.invokeHandlerAddedIfNeeded();//如果在注册前有处理器添加，还没进行HandlerAdded回调，注册成功后要回调
//
//            safeSetSuccess(promise);//回调注册成功
//            pipeline.fireChannelRegistered();//通道注册事件传递
//
//            if (isActive()) {//通道激活的话
//                if (firstRegistration) {//第一次注册要进行激活事件传递
//                    pipeline.fireChannelActive();
//                } else if (config().isAutoRead()) {//否则如果设置了自动读，就进行读监听
//
//                    beginRead();
//                }
//            }
//        } catch (Throwable t) {
//
//            closeForcibly();//强制关闭
//            closeFuture.setClosed();//关闭回调
//            safeSetFailure(promise, t);//设置失败
//        }
//    }
//
//    @Override
//    public final void bind(final SocketAddress localAddress, final ChannelPromise promise) {
//			...
//
//        boolean wasActive = isActive();
//        try {
//            doBind(localAddress);
//        } catch (Throwable t) {
//            safeSetFailure(promise, t);
//            closeIfClosed();
//            return;
//        }
//
//        if (!wasActive && isActive()) {//绑定前没激活，绑定后激活了
//            invokeLater(new Runnable() {
//                @Override
//                public void run() {
//                    pipeline.fireChannelActive();
//                }
//            });
//        }
//
//        safeSetSuccess(promise);
//    }
//
//    @Override
//    public final void disconnect(final ChannelPromise promise) {
//        assertEventLoop();
//
//        if (!promise.setUncancellable()) {
//            return;
//        }
//
//        boolean wasActive = isActive();
//        try {
//            doDisconnect();
//            // Reset remoteAddress and localAddress
//            remoteAddress = null;
//            localAddress = null;
//        } catch (Throwable t) {
//            safeSetFailure(promise, t);
//            closeIfClosed();
//            return;
//        }
//
//        if (wasActive && !isActive()) {
//            invokeLater(new Runnable() {
//                @Override
//                public void run() {
//                    pipeline.fireChannelInactive();
//                }
//            });
//        }
//
//        safeSetSuccess(promise);
//        closeIfClosed(); // doDisconnect() might have closed the channel
//    }
//
//    @Override
//    public final void close(final ChannelPromise promise) {
//        assertEventLoop();
//
//        ClosedChannelException closedChannelException = new ClosedChannelException();
//        close(promise, closedChannelException, closedChannelException, false);
//    }
//
//    private void close(final ChannelPromise promise, final Throwable cause,
//                       final ClosedChannelException closeCause, final boolean notify) {
//        if (!promise.setUncancellable()) {
//            return;
//        }
//
//        if (closeInitiated) {//如果已经发起关闭了
//            if (closeFuture.isDone()) {//判断是否关闭完成
//                // Closed already.
//                safeSetSuccess(promise);//回调
//            } else if (!(promise instanceof VoidChannelPromise)) {
//                closeFuture.addListener(new ChannelFutureListener() {//如果不是VoidChannelPromise，添加关闭监听
//                    @Override
//                    public void operationComplete(ChannelFuture future) throws Exception {
//                        promise.setSuccess();
//                    }
//                });
//            }
//            return;
//        }
//
//        closeInitiated = true;//已经开始关闭了
//        //处理出站缓冲区关闭
//        final boolean wasActive = isActive();
//        final ChannelOutboundBuffer outboundBuffer = this.outboundBuffer;
//        this.outboundBuffer = null; // Disallow adding any messages and flushes to outboundBuffer.
//        Executor closeExecutor = prepareToClose();
//        if (closeExecutor != null) {
//            closeExecutor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        // Execute the close.
//                        doClose0(promise);
//                    } finally {
//
//                        invokeLater(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (outboundBuffer != null) {
//                                    // Fail all the queued messages
//                                    outboundBuffer.failFlushed(cause, notify);
//                                    outboundBuffer.close(closeCause);
//                                }
//                                fireChannelInactiveAndDeregister(wasActive);
//                            }
//                        });
//                    }
//                }
//            });
//        } else {
//            try {
//
//                doClose0(promise);
//            } finally {
//                if (outboundBuffer != null) {
//                    // Fail all the queued messages.
//                    outboundBuffer.failFlushed(cause, notify);
//                    outboundBuffer.close(closeCause);
//                }
//            }
//            if (inFlush0) {
//                invokeLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        fireChannelInactiveAndDeregister(wasActive);
//                    }
//                });
//            } else {
//                fireChannelInactiveAndDeregister(wasActive);
//            }
//        }
//    }
//
//    private void doClose0(ChannelPromise promise) {
//        try {
//            doClose();
//            closeFuture.setClosed();
//            safeSetSuccess(promise);
//        } catch (Throwable t) {
//            closeFuture.setClosed();
//            safeSetFailure(promise, t);
//        }
//    }
//
//    private void fireChannelInactiveAndDeregister(final boolean wasActive) {
//        deregister(voidPromise(), wasActive && !isActive());
//    }
//
//    private void deregister(final ChannelPromise promise, final boolean fireChannelInactive) {
//        if (!promise.setUncancellable()) {
//            return;
//        }
//
//        if (!registered) {
//            safeSetSuccess(promise);
//            return;
//        }
//        invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    doDeregister();
//                } catch (Throwable t) {
//                    logger.warn("Unexpected exception occurred while deregistering a channel.", t);
//                } finally {
//                    if (fireChannelInactive) {
//                        pipeline.fireChannelInactive();
//                    }
//
//                    if (registered) {
//                        registered = false;
//                        pipeline.fireChannelUnregistered();
//                    }
//                    safeSetSuccess(promise);
//                }
//            }
//        });
//    }
//
//    @UnstableApi
//    public final void shutdownOutput(final ChannelPromise promise) {
//        assertEventLoop();
//        shutdownOutput(promise, null);
//    }
//
//    private void shutdownOutput(final ChannelPromise promise, Throwable cause) {
//        if (!promise.setUncancellable()) {
//            return;
//        }
//
//        final ChannelOutboundBuffer outboundBuffer = this.outboundBuffer;
//        if (outboundBuffer == null) {//如果出站缓冲区为null的话，就回调失败
//            promise.setFailure(new ClosedChannelException());
//            return;
//        }
//        this.outboundBuffer = null; // Disallow adding any messages and flushes to outboundBuffer.禁止添加数据到出站缓冲区了
//
//        final Throwable shutdownCause = cause == null ?//根据异常创建ChannelOutputShutdownException
//                new ChannelOutputShutdownException("Channel output shutdown") :
//                new ChannelOutputShutdownException("Channel output shutdown", cause);
//        Executor closeExecutor = prepareToClose();//有关闭执行器
//        if (closeExecutor != null) {//提交一个任务
//            closeExecutor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        // Execute the shutdown.
//                        doShutdownOutput();
//                        promise.setSuccess();
//                    } catch (Throwable err) {
//                        promise.setFailure(err);
//                    } finally {//出站缓冲区事件任务
//                        // Dispatch to the EventLoop
//                        eventLoop().execute(new Runnable() {
//                            @Override
//                            public void run() {//出站缓冲区事件处理
//                                closeOutboundBufferForShutdown(pipeline, outboundBuffer, shutdownCause);
//                            }
//                        });
//                    }
//                }
//            });
//        } else {
//            try {//直接处理关闭
//                // Execute the shutdown.
//                doShutdownOutput();
//                promise.setSuccess();
//            } catch (Throwable err) {
//                promise.setFailure(err);
//            } finally {
//                closeOutboundBufferForShutdown(pipeline, outboundBuffer, shutdownCause);
//            }
//        }
//    }
//
//    private void closeOutboundBufferForShutdown(
//            ChannelPipeline pipeline, ChannelOutboundBuffer buffer, Throwable cause) {
//        buffer.failFlushed(cause, false);//不能冲刷
//        buffer.close(cause, true);//关闭出站缓冲区
//        pipeline.fireUserEventTriggered(ChannelOutputShutdownEvent.INSTANCE);//传递事件
//    }
//}
