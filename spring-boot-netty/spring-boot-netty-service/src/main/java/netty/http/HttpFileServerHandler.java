package netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;
import jakarta.activation.MimetypesFileTypeMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpUtil.isKeepAlive;
import static io.netty.handler.codec.http.HttpUtil.setContentLength;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author shenlx
 * @description
 * @date 2024/2/27 16:39
 */
public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

//    private final String url;

//    public HttpFileServerHandler(String url) {
//        this.url = url;
//    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        /**
         * 首先对HTTP请求消息的解码结果进行判断，如果解码失败，直接构造HTTP400错误返回。
         */
        if (!request.getDecoderResult().isSuccess()) {
            sendError(ctx, HttpResponseStatus.BAD_REQUEST);
            return;
        }
        /**
         * 对请求行中的方法进行判断，如果不是从浏览器或者表单设置为GET发起的请求（例如POST），则构造HTTP 405错误返回
         */
        if (request.getMethod() != HttpMethod.GET) {
            sendError(ctx, HttpResponseStatus.METHOD_NOT_ALLOWED);
            return;
        }
        /***
         * 对请求URL进行包装，然后对sanitizeUri方法展开分析
         */
        final String uri = request.getUri();
        final String path = sanitizeUri(uri);
        /**
         * 如果构造的URI不合法，贝IJ返回HTTP403错误。
         */
        if (path == null) {
            sendError(ctx, HttpResponseStatus.FORBIDDEN);
            return;
        }
        /**
         * 使用新组装的URI路径构造File对象
         */
        File file = new File(path);
        /**
         * 如果文件不存在或者是系统隐藏文件，则构造HTTP404异常返回。如果文件是目录，则发送目录的链接给客户端浏览器。下面我们重点分析返回文件链接响应给客户端的代码
         */
        if (file.isHidden() || !file.exists()) {
            sendError(ctx, HttpResponseStatus.NOT_FOUND);
            return;
        }
        if (file.isDirectory()) {
            if (uri.endsWith("/")) {
                sendListing(ctx, file);
            } else {
                sendRedirect(ctx, uri + '/');
            }
            return;
        }
        /**
         * 对超链接的文件进行合法性判断，如果不是合法文件，则返回HTTP403错误。校验通过后
         */
        if (!file.isFile()) {
            sendError(ctx, HttpResponseStatus.FORBIDDEN);
            return;
        }
        RandomAccessFile randomAccessFile = null;
        /**
         * 使用随机文件读写类以只读的方式打开文件，如果文件打开失败，则返回HTTP 404错误
         */
        try {
            //r 代表以只读文件打开
            new RandomAccessFile(file, "r");
        } catch (FileNotFoundException f) {
            sendError(ctx, HttpResponseStatus.NOT_FOUND);
            return;
        }
        /**
         * 获取文件的长度，构造成功的HTTP应答消息，然后在消息头中设置contentlength和contenttype，判断是否是Keep-Alive
         * ，如果是，则在应答消息头中设置Connection为Keep-Alive
         */
        long length = randomAccessFile.length();
        HttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.OK);
        setContentLength(response,length);
        setContentTypeHeader(response,file);
        if(isKeepAlive(request)){
            response.headers().set(CONNECTION,HttpHeaders.Values.KEEP_ALIVE);
        }
        /***
         * 发送响应消息
         */
        ctx.write(response);

        /**
         * 通过Netty的ChunkedFile对象直接将文件写入到发送缓冲区中。最后为sendFileFuture增加GenericFutureListener
         * ，如果发送完成，打印“Transfercomplete.”。如果使用chunked编码，最后需要发送一个编码结束的空消息体
         * ，将LastHt叩Content的EMPTY_LAST_CONTENT发送到缓冲区中，标识所有的消息体已经发送完成
         * ，同时调用flush方法将之前在发送缓冲区的消息刷新到SocketChannel中发送给对方
         */
        ChannelFuture channelFuture;
        channelFuture=ctx.write(new ChunkedFile(randomAccessFile,0,length,8192)
                ,ctx.newProgressivePromise());
        channelFuture.addListener(new ChannelProgressiveFutureListener() {
            @Override
            public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) throws Exception {
                if(total<0){
                    System.err.println("Transfer progress:"+progress);
                }else{
                    System.err.println("Transfer progress:"+progress + "/" + total);
                }
            }

            @Override
            public void operationComplete(ChannelProgressiveFuture future) throws Exception {
                System.out.println("Transfer complete");
            }
        });

        ChannelFuture lastChannelFuture=ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        /**
         * 如果是非Keep田Alive的，最后一包消息发送完成之后，服务端要主动关闭连接。服务端的代码已经介绍完毕
         */
        if(!isKeepAlive(request)){
            lastChannelFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
     public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
         throws Exception {
             cause.printStackTrace();
             if (ctx.channel().isActive()) {
                     sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
                 }
             }

    private static final Pattern INSECURE_URI = Pattern.compile(".*[<>&\"].*");

    private String sanitizeUri(String uri) {
        /**
         * 首先使用JDK的java.net.URLDecoder对URL进行解码，使用UTF-8字符集，解码成功之后对URI进行合法性判断，如果URI与允许访问的URI一致或者是其子目录（文件〉，则校验通过，否则返回空
         */
        uri = URLDecoder.decode(uri, StandardCharsets.UTF_8);

        if (!uri.startsWith(uri)) {
            return null;
        }

        if (!uri.startsWith("/")) {
            return null;
        }
        /**
         * 将硬编码的文件路径分隔符替换为本地操作系统的文件路径分隔符
         */
        uri = uri.replace("/", File.separator);
        /**
         * 对新的URI做二次合法性校验，如果校验失败则直接返回空。
         */
        if (uri.contains(File.separator + ".")
                || uri.contains("." + File.separator) || uri.startsWith(".")
                || uri.endsWith(".") || INSECURE_URI.matcher(uri).matches()) {
            return null;
        }
        /**
         * 最后对文件进行拼接，使用当前运行程序所在的工程目录＋URI构造绝对路径返回。
         */
        return System.getProperty("user.dir") + File.separator + uri;
    }

    private static final Pattern ALLOWED_FILE_NAME = Pattern.compile("[A-Za-z0-9][-_A-Za-z0-9\\.]*");

    private static void sendListing(ChannelHandlerContext ctx, File dir) {
        /**
         * 用于构造响应消息体，由于需要将响应结果显示在浏览器上，所以采用了HTML的格式
         */
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.OK);
        response.headers().set(CONTENT_TYPE, "text/html;charset=UTF-8");
        StringBuilder buf = new StringBuilder();
        String dirPath = dir.getPath();
        buf.append("<!DOCTYPE html>\r\n");
        buf.append("<html><head><title>");
        buf.append(dirPath);
        buf.append(" 目录：");
        buf.append("</title></head><body>\r\n");
        buf.append("<h3>");
        buf.append(dirPath).append(" 目录：");
        buf.append("</h3>\r\n");
        buf.append("<ul>");
        /**
         * 打印了一个．的链接
         */
        buf.append("<li>链接：<a href=\"../\">..</a></li>\r\n");
        for (File f : dir.listFiles()) {
            /**
             * 用于展示根目录下的所有文件和文件夹，同时使用超链接来标识
             */
            if (f.isHidden() || !f.canRead()) {
                continue;
            }
            String name = f.getName();
            if (!ALLOWED_FILE_NAME.matcher(name).matches()) {
                continue;
            }
            buf.append("<li>链接：<a href=\"");
            buf.append(name);
            buf.append("\">");
            buf.append(name);
            buf.append("</a></li>\r\n");
        }
        buf.append("</ul></body></html>\r\n");
        /***
         * 分配对应消息的缓冲对象
         */
        ByteBuf buffer = Unpooled.copiedBuffer(buf, CharsetUtil.UTF_8);
        /**
         * 行将缓冲区中的响应消息存放到HTTP    应答消息中，然后释放缓冲区，最后调用writeAndFlush将响应消息发送到缓冲区并刷新到Socket.Channel中。
         * 如果用户在浏览器上点击超链接直接打开或者下载文件
         */
        response.content().writeBytes(buffer);
        buffer.release();
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private static void sendRedirect(ChannelHandlerContext ctx,String newUri){
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.FOUND);
        response.headers().set(LOCATION,newUri);
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }


    private static void sendError(ChannelHandlerContext ctx,
                                  HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
                status, Unpooled.copiedBuffer("Failure: " + status
                + "\r\n", CharsetUtil.UTF_8));
        /**
         * 首先创建成功的HTTP 响应消息，随后设置消息头的类型为“text/html:charset=UTF-8”。
         */
        response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private static void setContentTypeHeader(HttpResponse response, File file) {
        MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
        response.headers().set(CONTENT_TYPE,
                mimeTypesMap.getContentType(file.getPath()));
    }
}
