package netty.xml.demo02.untils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;

/**
 * @author shenlx
 * @description
 * @date 2024/3/1 13:48
 */
public abstract class AbstractHttpXmlEncoder <T> extends MessageToMessageEncoder<T> {
    IBindingFactory factory = null;
    StringWriter writer = null;
    final static String CHARSET_NAME = "UTF-8";
    final static Charset UTF_8 = Charset.forName(CHARSET_NAME);


    protected ByteBuf encode0(ChannelHandlerContext contextReq, Object body) throws JiBXException, IOException {
        factory = BindingDirectory.getFactory(body.getClass());
        writer = new StringWriter();
        IMarshallingContext context = factory.createMarshallingContext();
        context.setIndent(2);
        //将业务POJO（Order）类实例化为XML字符串。
        context.marshalDocument(body, CHARSET_NAME, null, writer);
        String xmlStr = writer.toString();
        writer.close();
        writer = null;
        //将字符串包装成Netty的ByteBuf并返回，实现了HTTP请求消息的XML编码
        ByteBuf encodeBuf = Unpooled.copiedBuffer(xmlStr, UTF_8);
        return encodeBuf;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable throwable) throws IOException {
        //释放资源
        if (writer != null) {
            writer.close();
            writer = null;
        }
    }

}
