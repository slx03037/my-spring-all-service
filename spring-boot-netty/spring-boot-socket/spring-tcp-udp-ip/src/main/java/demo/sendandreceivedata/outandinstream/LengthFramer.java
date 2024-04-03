package demo.sendandreceivedata.outandinstream;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author shenlx
 * @description
 * @date 2024/1/18 10:18
 */
public class LengthFramer implements Framer{
    public static final int Maxmessagelength=65536;
    public static final int BYTEMASK = 0xff;

    public static final int SHORTMASK = 0xffff;

    public static final int BYTESHIFT= 8;

    private final DataInputStream in;

    /**
     *构造函数
     * 获取帧消息的输入流，并将其包裹子啊一个DataInputStream中
     */
    public LengthFramer(InputStream in)throws IOException{
        this.in=new DataInputStream(in);
    }

    /**
     * frameMsg方法用力啊添加成帧信息
     * 检验消息长度：由于用的是长为两个字节的字段，因此消息的长度不能超过65535(注意该值太大而不能存入一个short型证数中，因此我们每次只向输出流写一个字节)
     *
     * 输出长度字段：添加长度信息(无符号short型整数)前缀，输出消息的字节数
     *
     * 输出消息：nextMsg()方法用于从输入流中提取下一帧
     *
     * 读取长度前缀：readUnsignedShort方法读取两个字节，将他们作为big-end整数进行解释，并以int型整数返回它们的值
     *
     * 读取指定数量的字节:readfully()将阻塞等待，知道消息到足够的字节来填满指定的数组
     *
     * 以自己的形式返回消息
     *
     */
    @Override
    public void frameMsg(byte[] message, OutputStream out) throws IOException {
        if(message.length>Maxmessagelength){
            throw new IOException("message too long");
        }
        out.write((message.length>>BYTESHIFT) & BYTEMASK);
        out.write(message.length & BYTEMASK);
        out.write(message);
        out.flush();
    }

    @Override
    public byte[] nextMsg() throws IOException {
        int lenth;
        try{
            lenth=in.readUnsignedShort();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
        byte[]msg=new byte[lenth];
        in.readFully(msg);
        return msg;

    }
}
