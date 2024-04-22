//package netty.sever;
//
//import io.netty.channel.*;
//import io.netty.channel.socket.ChannelOutputShutdownEvent;
//import io.netty.channel.socket.ChannelOutputShutdownException;
//import io.netty.util.ReferenceCountUtil;
//import io.netty.util.internal.ObjectUtil;
//import io.netty.util.internal.PlatformDependent;
//import io.netty.util.internal.UnstableApi;
//
//import java.io.IOException;
//import java.net.*;
//import java.nio.channels.ClosedChannelException;
//import java.nio.channels.NotYetConnectedException;
//import java.util.concurrent.Executor;
//import java.util.concurrent.RejectedExecutionException;
//
///**
// * @author shenlx
// * @description
// * @date 2024/4/22 14:32
// */
//public abstract class AbstractUnsafeBak implements Unsafe{
//    private volatile EventLoop eventLoop;
//    private volatile boolean registered;
//    private volatile ChannelOutboundBuffer outboundBuffer = new ChannelOutboundBuffer(AbstractChannel.this);
//    private RecvByteBufAllocator.Handle recvHandle;
//    private boolean inFlush0;
//    /** true if the channel has never been registered, false otherwise */
//    private boolean neverRegistered = true;
//
//    private void assertEventLoop() {
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
//    public final ChannelOutboundBuffer outboundBuffer() {
//        return outboundBuffer;
//    }
//
//    @Override
//    public final SocketAddress localAddress() {
//        return localAddress0();
//    }
//
//    @Override
//    public final SocketAddress remoteAddress() {
//        return remoteAddress0();
//    }
//
//    @Override
//    public final void register(EventLoop eventLoop, final ChannelPromise promise) {
//        ObjectUtil.checkNotNull(eventLoop, "eventLoop");
//        if (isRegistered()) {
//            promise.setFailure(new IllegalStateException("registered to an event loop already"));
//            return;
//        }
//        if (!isCompatible(eventLoop)) {
//            promise.setFailure(
//                    new IllegalStateException("incompatible event loop type: " + eventLoop.getClass().getName()));
//            return;
//        }
//
//        AbstractChannel.this.eventLoop = eventLoop;
//
//        if (eventLoop.inEventLoop()) {
//            register0(promise);
//        } else {
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
//            // check if the channel is still open as it could be closed in the mean time when the register
//            // call was outside of the eventLoop
//            if (!promise.setUncancellable() || !ensureOpen(promise)) {
//                return;
//            }
//            boolean firstRegistration = neverRegistered;
//            doRegister();
//            neverRegistered = false;
//            registered = true;
//
//            // Ensure we call handlerAdded(...) before we actually notify the promise. This is needed as the
//            // user may already fire events through the pipeline in the ChannelFutureListener.
//            pipeline.invokeHandlerAddedIfNeeded();
//
//            safeSetSuccess(promise);
//            pipeline.fireChannelRegistered();
//            // Only fire a channelActive if the channel has never been registered. This prevents firing
//            // multiple channel actives if the channel is deregistered and re-registered.
//            if (isActive()) {
//                if (firstRegistration) {
//                    pipeline.fireChannelActive();
//                } else if (config().isAutoRead()) {
//                    // This channel was registered before and autoRead() is set. This means we need to begin read
//                    // again so that we process inbound data.
//                    //
//                    // See https://github.com/netty/netty/issues/4805
//                    beginRead();
//                }
//            }
//        } catch (Throwable t) {
//            // Close the channel directly to avoid FD leak.
//            closeForcibly();
//            closeFuture.setClosed();
//            safeSetFailure(promise, t);
//        }
//    }
//
//    @Override
//    public final void bind(final SocketAddress localAddress, final ChannelPromise promise) {
//        assertEventLoop();
//
//        if (!promise.setUncancellable() || !ensureOpen(promise)) {
//            return;
//        }
//
//        // See: https://github.com/netty/netty/issues/576
//        if (Boolean.TRUE.equals(config().getOption(ChannelOption.SO_BROADCAST)) &&
//                localAddress instanceof InetSocketAddress &&
//                !((InetSocketAddress) localAddress).getAddress().isAnyLocalAddress() &&
//                !PlatformDependent.isWindows() && !PlatformDependent.maybeSuperUser()) {
//            // Warn a user about the fact that a non-root user can't receive a
//            // broadcast packet on *nix if the socket is bound on non-wildcard address.
//            logger.warn(
//                    "A non-root user can't receive a broadcast packet if the socket " +
//                            "is not bound to a wildcard address; binding to a non-wildcard " +
//                            "address (" + localAddress + ") anyway as requested.");
//        }
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
//        if (!wasActive && isActive()) {
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
//    public void close(final ChannelPromise promise) {
//        assertEventLoop();
//
//        ClosedChannelException closedChannelException =
//                StacklessClosedChannelException.newInstance(AbstractChannel.class, "close(ChannelPromise)");
//        close(promise, closedChannelException, closedChannelException, false);
//    }
//
//    /**
//     * Shutdown the output portion of the corresponding {@link Channel}.
//     * For example this will clean up the {@link ChannelOutboundBuffer} and not allow any more writes.
//     */
//    @UnstableApi
//    public final void shutdownOutput(final ChannelPromise promise) {
//        assertEventLoop();
//        shutdownOutput(promise, null);
//    }
//
//    /**
//     * Shutdown the output portion of the corresponding {@link Channel}.
//     * For example this will clean up the {@link ChannelOutboundBuffer} and not allow any more writes.
//     * @param cause The cause which may provide rational for the shutdown.
//     */
//    private void shutdownOutput(final ChannelPromise promise, Throwable cause) {
//        if (!promise.setUncancellable()) {
//            return;
//        }
//
//        final ChannelOutboundBuffer outboundBuffer = this.outboundBuffer;
//        if (outboundBuffer == null) {
//            promise.setFailure(new ClosedChannelException());
//            return;
//        }
//        this.outboundBuffer = null; // Disallow adding any messages and flushes to outboundBuffer.
//
//        final Throwable shutdownCause = cause == null ?
//                new ChannelOutputShutdownException("Channel output shutdown") :
//                new ChannelOutputShutdownException("Channel output shutdown", cause);
//
//        // When a side enables SO_LINGER and calls showdownOutput(...) to start TCP half-closure
//        // we can not call doDeregister here because we should ensure this side in fin_wait2 state
//        // can still receive and process the data which is send by another side in the close_wait state。
//        // See https://github.com/netty/netty/issues/11981
//        try {
//            // The shutdown function does not block regardless of the SO_LINGER setting on the socket
//            // so we don't need to use GlobalEventExecutor to execute the shutdown
//            doShutdownOutput();
//            promise.setSuccess();
//        } catch (Throwable err) {
//            promise.setFailure(err);
//        } finally {
//            closeOutboundBufferForShutdown(pipeline, outboundBuffer, shutdownCause);
//        }
//    }
//
//    private void closeOutboundBufferForShutdown(
//            ChannelPipeline pipeline, ChannelOutboundBuffer buffer, Throwable cause) {
//        buffer.failFlushed(cause, false);
//        buffer.close(cause, true);
//        pipeline.fireUserEventTriggered(ChannelOutputShutdownEvent.INSTANCE);
//    }
//
//    private void close(final ChannelPromise promise, final Throwable cause,
//                       final ClosedChannelException closeCause, final boolean notify) {
//        if (!promise.setUncancellable()) {
//            return;
//        }
//
//        if (closeInitiated) {
//            if (closeFuture.isDone()) {
//                // Closed already.
//                safeSetSuccess(promise);
//            } else if (!(promise instanceof VoidChannelPromise)) { // Only needed if no VoidChannelPromise.
//                // This means close() was called before so we just register a listener and return
//                closeFuture.addListener(new ChannelFutureListener() {
//                    @Override
//                    public void operationComplete(ChannelFuture future) throws Exception {
//                        promise.setSuccess();
//                    }
//                });
//            }
//            return;
//        }
//
//        closeInitiated = true;
//
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
//                        // Call invokeLater so closeAndDeregister is executed in the EventLoop again!
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
//                // Close the channel and fail the queued messages in all cases.
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
//    @Override
//    public final void closeForcibly() {
//        assertEventLoop();
//
//        try {
//            doClose();
//        } catch (Exception e) {
//            logger.warn("Failed to close a channel.", e);
//        }
//    }
//
//    @Override
//    public final void deregister(final ChannelPromise promise) {
//        assertEventLoop();
//
//        deregister(promise, false);
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
//
//        // As a user may call deregister() from within any method while doing processing in the ChannelPipeline,
//        // we need to ensure we do the actual deregister operation later. This is needed as for example,
//        // we may be in the ByteToMessageDecoder.callDecode(...) method and so still try to do processing in
//        // the old EventLoop while the user already registered the Channel to a new EventLoop. Without delay,
//        // the deregister operation this could lead to have a handler invoked by different EventLoop and so
//        // threads.
//        //
//        // See:
//        // https://github.com/netty/netty/issues/4435
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
//                    // Some transports like local and AIO does not allow the deregistration of
//                    // an open channel.  Their doDeregister() calls close(). Consequently,
//                    // close() calls deregister() again - no need to fire channelUnregistered, so check
//                    // if it was registered.
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
//    @Override
//    public final void beginRead() {
//        assertEventLoop();
//
//        try {
//            doBeginRead();
//        } catch (final Exception e) {
//            invokeLater(new Runnable() {
//                @Override
//                public void run() {
//                    pipeline.fireExceptionCaught(e);
//                }
//            });
//            close(voidPromise());
//        }
//    }
//
//    @Override
//    public final void write(Object msg, ChannelPromise promise) {
//        assertEventLoop();
//
//        ChannelOutboundBuffer outboundBuffer = this.outboundBuffer;
//        if (outboundBuffer == null) {
//            try {
//                // release message now to prevent resource-leak
//                ReferenceCountUtil.release(msg);
//            } finally {
//                // If the outboundBuffer is null we know the channel was closed and so
//                // need to fail the future right away. If it is not null the handling of the rest
//                // will be done in flush0()
//                // See https://github.com/netty/netty/issues/2362
//                safeSetFailure(promise,
//                        newClosedChannelException(initialCloseCause, "write(Object, ChannelPromise)"));
//            }
//            return;
//        }
//
//        int size;
//        try {
//            msg = filterOutboundMessage(msg);
//            size = pipeline.estimatorHandle().size(msg);
//            if (size < 0) {
//                size = 0;
//            }
//        } catch (Throwable t) {
//            try {
//                ReferenceCountUtil.release(msg);
//            } finally {
//                safeSetFailure(promise, t);
//            }
//            return;
//        }
//
//        outboundBuffer.addMessage(msg, size, promise);
//    }
//
//    @Override
//    public final void flush() {
//        assertEventLoop();
//
//        ChannelOutboundBuffer outboundBuffer = this.outboundBuffer;
//        if (outboundBuffer == null) {
//            return;
//        }
//
//        outboundBuffer.addFlush();
//        flush0();
//    }
//
//    @SuppressWarnings("deprecation")
//    protected void flush0() {
//        if (inFlush0) {
//            // Avoid re-entrance
//            return;
//        }
//
//        final ChannelOutboundBuffer outboundBuffer = this.outboundBuffer;
//        if (outboundBuffer == null || outboundBuffer.isEmpty()) {
//            return;
//        }
//
//        inFlush0 = true;
//
//        // Mark all pending write requests as failure if the channel is inactive.
//        if (!isActive()) {
//            try {
//                // Check if we need to generate the exception at all.
//                if (!outboundBuffer.isEmpty()) {
//                    if (isOpen()) {
//                        outboundBuffer.failFlushed(new NotYetConnectedException(), true);
//                    } else {
//                        // Do not trigger channelWritabilityChanged because the channel is closed already.
//                        outboundBuffer.failFlushed(newClosedChannelException(initialCloseCause, "flush0()"), false);
//                    }
//                }
//            } finally {
//                inFlush0 = false;
//            }
//            return;
//        }
//
//        try {
//            doWrite(outboundBuffer);
//        } catch (Throwable t) {
//            handleWriteError(t);
//        } finally {
//            inFlush0 = false;
//        }
//    }
//
//    protected final void handleWriteError(Throwable t) {
//        if (t instanceof IOException && config().isAutoClose()) {
//            /**
//             * Just call {@link #close(ChannelPromise, Throwable, boolean)} here which will take care of
//             * failing all flushed messages and also ensure the actual close of the underlying transport
//             * will happen before the promises are notified.
//             *
//             * This is needed as otherwise {@link #isActive()} , {@link #isOpen()} and {@link #isWritable()}
//             * may still return {@code true} even if the channel should be closed as result of the exception.
//             */
//            initialCloseCause = t;
//            close(voidPromise(), t, newClosedChannelException(t, "flush0()"), false);
//        } else {
//            try {
//                shutdownOutput(voidPromise(), t);
//            } catch (Throwable t2) {
//                initialCloseCause = t;
//                close(voidPromise(), t2, newClosedChannelException(t, "flush0()"), false);
//            }
//        }
//    }
//
//    private ClosedChannelException newClosedChannelException(Throwable cause, String method) {
//        ClosedChannelException exception =
//                StacklessClosedChannelException.newInstance(AbstractChannel.AbstractUnsafe.class, method);
//        if (cause != null) {
//            exception.initCause(cause);
//        }
//        return exception;
//    }
//
//    @Override
//    public final ChannelPromise voidPromise() {
//        assertEventLoop();
//
//        return unsafeVoidPromise;
//    }
//
//    protected final boolean ensureOpen(ChannelPromise promise) {
//        if (isOpen()) {
//            return true;
//        }
//
//        safeSetFailure(promise, newClosedChannelException(initialCloseCause, "ensureOpen(ChannelPromise)"));
//        return false;
//    }
//
//    /**
//     * Marks the specified {@code promise} as success.  If the {@code promise} is done already, log a message.
//     */
//    protected final void safeSetSuccess(ChannelPromise promise) {
//        if (!(promise instanceof VoidChannelPromise) && !promise.trySuccess()) {
//            logger.warn("Failed to mark a promise as success because it is done already: {}", promise);
//        }
//    }
//
//    /**
//     * Marks the specified {@code promise} as failure.  If the {@code promise} is done already, log a message.
//     */
//    protected final void safeSetFailure(ChannelPromise promise, Throwable cause) {
//        if (!(promise instanceof VoidChannelPromise) && !promise.tryFailure(cause)) {
//            logger.warn("Failed to mark a promise as failure because it's done already: {}", promise, cause);
//        }
//    }
//
//    protected final void closeIfClosed() {
//        if (isOpen()) {
//            return;
//        }
//        close(voidPromise());
//    }
//
//    private void invokeLater(Runnable task) {
//        try {
//            // This method is used by outbound operation implementations to trigger an inbound event later.
//            // They do not trigger an inbound event immediately because an outbound operation might have been
//            // triggered by another inbound event handler method.  If fired immediately, the call stack
//            // will look like this for example:
//            //
//            //   handlerA.inboundBufferUpdated() - (1) an inbound handler method closes a connection.
//            //   -> handlerA.ctx.close()
//            //      -> channel.unsafe.close()
//            //         -> handlerA.channelInactive() - (2) another inbound handler method called while in (1) yet
//            //
//            // which means the execution of two inbound handler methods of the same handler overlap undesirably.
//            eventLoop().execute(task);
//        } catch (RejectedExecutionException e) {
//            logger.warn("Can't invoke task later as EventLoop rejected it", e);
//        }
//    }
//
//    /**
//     * Appends the remote address to the message of the exceptions caused by connection attempt failure.
//     */
//    protected final Throwable annotateConnectException(Throwable cause, SocketAddress remoteAddress) {
//        if (cause instanceof ConnectException) {
//            return new AbstractChannel.AnnotatedConnectException((ConnectException) cause, remoteAddress);
//        }
//        if (cause instanceof NoRouteToHostException) {
//            return new AbstractChannel.AnnotatedNoRouteToHostException((NoRouteToHostException) cause, remoteAddress);
//        }
//        if (cause instanceof SocketException) {
//            return new AbstractChannel.AnnotatedSocketException((SocketException) cause, remoteAddress);
//        }
//
//        return cause;
//    }
//
//    /**
//     * Prepares to close the {@link Channel}. If this method returns an {@link Executor}, the
//     * caller must call the {@link Executor#execute(Runnable)} method with a task that calls
//     * {@link #doClose()} on the returned {@link Executor}. If this method returns {@code null},
//     * {@link #doClose()} must be called from the caller thread. (i.e. {@link EventLoop})
//     */
//    protected Executor prepareToClose() {
//        return null;
//    }
//
//    /**
//     * Return {@code true} if the given {@link EventLoop} is compatible with this instance.
//     */
//    protected abstract boolean isCompatible(EventLoop loop);
//
//    /**
//     * Returns the {@link SocketAddress} which is bound locally.
//     */
//    protected abstract SocketAddress localAddress0();
//
//    /**
//     * Return the {@link SocketAddress} which the {@link Channel} is connected to.
//     */
//    protected abstract SocketAddress remoteAddress0();
//
//    /**
//     * Is called after the {@link Channel} is registered with its {@link EventLoop} as part of the register process.
//     *
//     * Sub-classes may override this method
//     */
//    protected void doRegister() throws Exception {
//        // NOOP
//    }
//
//    /**
//     * Bind the {@link Channel} to the {@link SocketAddress}
//     */
//    protected abstract void doBind(SocketAddress localAddress) throws Exception;
//
//    /**
//     * Disconnect this {@link Channel} from its remote peer
//     */
//    protected abstract void doDisconnect() throws Exception;
//
//    /**
//     * Close the {@link Channel}
//     */
//    protected abstract void doClose() throws Exception;
//
//    /**
//     * Called when conditions justify shutting down the output portion of the channel. This may happen if a write
//     * operation throws an exception.
//     */
//    @UnstableApi
//    protected void doShutdownOutput() throws Exception {
//        doClose();
//    }
//
//    /**
//     * Deregister the {@link Channel} from its {@link EventLoop}.
//     *
//     * Sub-classes may override this method
//     */
//    protected void doDeregister() throws Exception {
//        // NOOP
//    }
//
//    /**
//     * Schedule a read operation.
//     */
//    protected abstract void doBeginRead() throws Exception;
//
//    /**
//     * Flush the content of the given buffer to the remote peer.
//     */
//    protected abstract void doWrite(ChannelOutboundBuffer in) throws Exception;
//
//    /**
//     * Invoked when a new message is added to a {@link ChannelOutboundBuffer} of this {@link AbstractChannel}, so that
//     * the {@link Channel} implementation converts the message to another. (e.g. heap buffer -> direct buffer)
//     */
//    protected Object filterOutboundMessage(Object msg) throws Exception {
//        return msg;
//    }
//
//    protected void validateFileRegion(DefaultFileRegion region, long position) throws IOException {
//        DefaultFileRegion.validate(region, position);
//    }
//
//    static final class CloseFuture extends DefaultChannelPromise {
//
//        CloseFuture(AbstractChannel ch) {
//            super(ch);
//        }
//
//        @Override
//        public ChannelPromise setSuccess() {
//            throw new IllegalStateException();
//        }
//
//        @Override
//        public ChannelPromise setFailure(Throwable cause) {
//            throw new IllegalStateException();
//        }
//
//        @Override
//        public boolean trySuccess() {
//            throw new IllegalStateException();
//        }
//
//        @Override
//        public boolean tryFailure(Throwable cause) {
//            throw new IllegalStateException();
//        }
//
//        boolean setClosed() {
//            return super.trySuccess();
//        }
//    }
//
//    private static final class AnnotatedConnectException extends ConnectException {
//
//        private static final long serialVersionUID = 3901958112696433556L;
//
//        AnnotatedConnectException(ConnectException exception, SocketAddress remoteAddress) {
//            super(exception.getMessage() + ": " + remoteAddress);
//            initCause(exception);
//        }
//
//        // Suppress a warning since this method doesn't need synchronization
//        @Override
//        public Throwable fillInStackTrace() {   // lgtm[java/non-sync-override]
//            return this;
//        }
//    }
//
//    private static final class AnnotatedNoRouteToHostException extends NoRouteToHostException {
//
//        private static final long serialVersionUID = -6801433937592080623L;
//
//        AnnotatedNoRouteToHostException(NoRouteToHostException exception, SocketAddress remoteAddress) {
//            super(exception.getMessage() + ": " + remoteAddress);
//            initCause(exception);
//        }
//
//        // Suppress a warning since this method doesn't need synchronization
//        @Override
//        public Throwable fillInStackTrace() {   // lgtm[java/non-sync-override]
//            return this;
//        }
//    }
//
//    private static final class AnnotatedSocketException extends SocketException {
//
//        private static final long serialVersionUID = 3896743275010454039L;
//
//        AnnotatedSocketException(SocketException exception, SocketAddress remoteAddress) {
//            super(exception.getMessage() + ": " + remoteAddress);
//            initCause(exception);
//        }
//
//        // Suppress a warning since this method doesn't need synchronization
//        @Override
//        public Throwable fillInStackTrace() {   // lgtm[java/non-sync-override]
//            return this;
//        }
//    }
//}
