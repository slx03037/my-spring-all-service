package netty.agreement.messagecode.encode;

import io.netty.buffer.ByteBuf;
import netty.agreement.datastructure.MarshallingCodecFactory;
import org.jboss.marshalling.Marshaller;

import java.io.IOException;

/**
 * @author shenlx
 * @description
 * @date 2024/3/29 11:32
 */
public class MarshallingEncoder  {
    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];

    Marshaller marshaller;

    public MarshallingEncoder()throws IOException {
        marshaller= MarshallingCodecFactory.buildMarshalling();
    }

    protected void encode(Object msg, ByteBuf out) throws IOException {
        //必须要知道当前的数据位置是哪: 起始数据位置
        //长度属性的位置索引
        int lengthPos = out.writerIndex();
        //占位写操作:先写一个4个字节的空的内容，记录在起始数据位置，用于设置内容长度
        out.writeBytes(LENGTH_PLACEHOLDER);
        ChannelBufferByteOutput output = new ChannelBufferByteOutput(out);
        marshaller.start(output);
        marshaller.writeObject(msg);
        marshaller.finish();
        marshaller.close();
        //总长度(结束位置) - 初始化长度(起始位置) - 预留的长度  = body数据长度
        out.setInt(lengthPos, out.writerIndex() - lengthPos - 4);
    }
}
