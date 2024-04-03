package netty.xml.demo02.untils;

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
 * @date 2024/3/1 13:44
 */
public abstract class AbstractHttpXmlDecoder <T> extends MessageToMessageDecoder<T> {
    private IBindingFactory factory;
    private StringReader reader;
    //解码对象类型
    private final Class<?> clazz;
    //是否打印消息体码流的开关，默认关闭
    private final boolean isPrint;
    private final static String CHARSET_NAME = "UTF-8";
    private final static Charset UTF_8 = Charset.forName(CHARSET_NAME);


    protected AbstractHttpXmlDecoder(Class<?> clazz) {
        this(clazz, false);
    }

    protected AbstractHttpXmlDecoder(Class<?> clazz, boolean isPrint) {
        this.clazz = clazz;
        this.isPrint = isPrint;
    }

    protected Object decode0(ChannelHandlerContext context, ByteBuf body) throws JiBXException {
        factory = BindingDirectory.getFactory(clazz);
        //从消息体中获取码流
        String content = body.toString(UTF_8);
        //通过isPrint 来判断是否打印消息体，主要是为了方便定位问题
        if (isPrint) {
            System.out.println("The body is : " + content);
        }

        reader = new StringReader(content);
        //通过JiBx 类库将XML转换为POJO对象。
        IUnmarshallingContext unmarshallingContext = factory.createUnmarshallingContext();
        Object result = unmarshallingContext.unmarshalDocument(reader);
        //释放资源
        reader.close();
        reader = null;
        return result;
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        //释放资源
        if (reader != null) {
            reader.close();
            reader = null;
        }
    }
}
