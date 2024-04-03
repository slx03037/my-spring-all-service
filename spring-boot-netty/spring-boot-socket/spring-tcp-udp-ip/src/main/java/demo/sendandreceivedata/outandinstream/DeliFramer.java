package demo.sendandreceivedata.outandinstream;

import java.io.*;

/**
 * @author shenlx
 * @description
 * @date 2024/1/18 9:53
 */
public class DeliFramer implements Framer{
    private final InputStream in;

    public DeliFramer(InputStream in){
        this.in=in;
    }

    private static final byte  DELIMTTER=10; //"\n"字节位10

    @Override
    public void frameMsg(byte[] message, OutputStream out) throws IOException {
        for(byte b:message){
            if(b==DELIMTTER){
                throw new IOException("message contains delimiter");
            }
        }
        out.write(message);
        out.write(DELIMTTER);
        out.flush();
    }

    @Override
    public byte[] nextMsg() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int nextByte;
        while((nextByte=in.read())!=DELIMTTER){
            if(nextByte== -1){
                if(byteArrayOutputStream.size() ==0){
                    return null;
                }else {
                    throw new EOFException("Noe-empty message without delimiter");
                }
            }
            byteArrayOutputStream.write(nextByte);
        }
        return byteArrayOutputStream.toByteArray();
    }
}
