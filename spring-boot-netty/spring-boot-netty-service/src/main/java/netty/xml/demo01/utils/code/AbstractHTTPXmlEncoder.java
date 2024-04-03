package netty.xml.demo01.utils.code;

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
 * @date 2024/2/29 14:34
 */
public abstract class AbstractHTTPXmlEncoder<T> extends MessageToMessageEncoder<T> {
    IBindingFactory factory=null;

    StringWriter writer=null;
    final static String CHARSET_NAME="UTF-8";

    final static Charset UTF_8=Charset.forName(CHARSET_NAME);



    protected  ByteBuf encode0(ChannelHandlerContext ctx, Object msg) throws JiBXException, IOException {
        factory= BindingDirectory.getFactory(msg.getClass());
        writer=new StringWriter();
        IMarshallingContext mctx = factory.createMarshallingContext();
        mctx.setIndent(2);
        mctx.marshalDocument(msg,CHARSET_NAME,null,writer);
        String xmlStr = writer.toString();
        writer.close();
        writer=null;
        ByteBuf byteBuf = Unpooled.copiedBuffer(xmlStr, UTF_8);
        return byteBuf;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("消息编码失败");
        if(writer !=null){
            writer.close();
            writer=null;
        }
    }

}
