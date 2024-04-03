package netty.xml.demo01.utils.code;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;

import java.io.StringReader;
import java.nio.charset.Charset;

/**
 * @author shenlx
 * @description
 * @date 2024/2/29 14:45
 */
public  abstract class AbstractHttpXmlDecoder <T> extends MessageToMessageDecoder<T> {
    private IBindingFactory factory;

    private StringReader reader;

    private final Class<?> clazz;

    private final boolean isPrint;

    private final static String CHARSET_NAME="UTF-8";

    private final static Charset UTF_8=Charset.forName(CHARSET_NAME);

    protected AbstractHttpXmlDecoder(Class<?> clazz){
        this(clazz,false);
    }

    protected AbstractHttpXmlDecoder(Class<?> clazz,boolean isPrint){
        this.clazz=clazz;
        this.isPrint=isPrint;
    }

    protected  Object decode0(ChannelHandlerContext ctx, ByteBuf msg) throws JiBXException {
        factory=BindingDirectory.getFactory(clazz);
        String context = msg.toString(UTF_8);
        if(isPrint) {
            System.out.println("The body is:" + context);
        }
        reader=new StringReader(context);
        IUnmarshallingContext uctx = factory.createUnmarshallingContext();
        Object result = uctx.unmarshalDocument(reader);
        reader.close();
        reader=null;
        return result;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("消息编码失败");
        if(reader !=null){
            reader.close();
            reader=null;
        }
    }

}
