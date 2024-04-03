package netty.agreement.messagecode.decode;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteInput;

import java.io.IOException;

/**
 * @author shenlx
 * @description
 * @date 2024/4/1 13:47
 */
public class ChannelBufferByteInput implements ByteInput {
    private final ByteBuf byteBuf;

    public ChannelBufferByteInput(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }

    @Override
    public int read() throws IOException {
        if (byteBuf.isReadable()) {
            return byteBuf.readByte() & 0xff;
        }
        return -1;
    }

    @Override
    public int read(byte[] bytes) throws IOException {
        return read(bytes, 0 , bytes.length);
    }

    @Override
    public int read(byte[] dst, int dstIndex, int length) throws IOException {
        int available = available();
        if (available == 0) {
            return -1;
        }

        length = Math.min(available, length);
        byteBuf.readBytes(dst, dstIndex, length);
        return length;
    }
    @Override
    public int available() throws IOException {
        return byteBuf.readableBytes();
    }

    @Override
    public long skip(long bytes) throws IOException {
        int readable = byteBuf.readableBytes();
        if (readable < bytes) {
            bytes = readable;
        }
        byteBuf.readerIndex((int) (byteBuf.readerIndex() + bytes));
        return bytes;
    }

    @Override
    public void close() throws IOException {

    }
}
