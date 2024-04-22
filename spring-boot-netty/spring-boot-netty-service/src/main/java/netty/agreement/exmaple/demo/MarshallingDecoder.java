package netty.agreement.exmaple.demo;

import io.netty.buffer.ByteBuf;
import netty.agreement.messagecode.decode.ChannelBufferByteInput;
import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.Unmarshaller;

import java.io.IOException;

/**
 * @author shenlx
 * @description
 * @date 2024/4/3 14:32
 */
public class MarshallingDecoder {
    private final Unmarshaller unmarshaller;

    /**
     * Creates a new decoder whose maximum object size is {@code 1048576} bytes.
     * If the size of the received object is greater than {@code 1048576} bytes,
     * a {@link IOException} will be raised.
     *
     * @throws IOException
     */
    public MarshallingDecoder() throws IOException {
        unmarshaller = MarshallingCodecFactory.buildUnMarshalling();
    }

    public Object decode(ByteBuf in) throws Exception {
        try {
            //1 首先读取4个长度(实际body内容长度)
            int bodySize = in.readInt();
            //2 获取实际body的缓冲内容
            int readIndex = in.readerIndex();
            ByteBuf buf = in.slice(readIndex, bodySize);
            //3 转换
            ChannelBufferByteInput input = new ChannelBufferByteInput(buf);
            //4 读取操作:
            this.unmarshaller.start(input);
            Object ret = this.unmarshaller.readObject();
            this.unmarshaller.finish();
            //5 读取完毕以后, 更新当前读取起始位置:
            //因为使用slice方法，原buf的位置还在readIndex上，故需要将位置重新设置一下
            in.readerIndex(in.readerIndex() + bodySize);

            return ret;

        } finally {
            this.unmarshaller.close();
        }
    }
}
