package demo.sendandreceivedata.bytes;

/**
 * @author shenlx
 * @description
 * @date 2024/1/17 17:40
 */
public class BruteForceCoding {
    private static final byte byteVal=101;
    private static final short shortVal=10001;

    private static final int intVal=10000001;

    private static final long longVal=1000000000001L;

    private final static int BSZIE=Byte.SIZE;

    private final static int SSZIE=Short.SIZE;

    private final static int ISZIE=Integer.SIZE;

    private final static int LSZIE=Long.SIZE;

    private final static int BYTEMASK=0xFF;

    /**
     * 给定数组中的每个字节作为一个无符号十进制数打印出来，BYTEMASK的作用是防止在字节数值转换成int类型时，发送符号扩展(sign-extended),即转换无符号整型
     */
    public static String byteArrayToDecimalString(byte[]array){
        StringBuilder stb = new StringBuilder();
        for(byte b:array){
            stb.append(b & BYTEMASK).append(" ");
        }
        return stb.toString();
    }

    /**
     * 赋值语句的右边，首先将数组向右移动，以使我们需要的字节处于该数值的低8位中，然后，将移位后的数转换位byte类型，
     * 并存入字节数组的适当位置，在转换过程中，除了低8位以外，其他位都将丢弃，这个过程将根据给定数组所占字节数迭代进行，该方法
     * 将返回存入数组后字节数组中新的偏移位置，因此我们不必做额外的工作来跟踪偏移量
     */
    public static int encodeIntBigEndian(byte[]dst,long val,int offset,int size){
        for(int i=0;i<size;i++){
            dst[offset++]=(byte)(val >> ((size-i-1)*Byte.SIZE));
        }
        return offset;
    }

    /**
     * 根据给定数组的字节大小进行迭代，通过每次迭代的左移操作，将所取得字节的值积累到一个long型整数中
     */
    public static long decodeIntBigEndian(byte[]val,int offset,int size){
        long stb=0;
        for(int i=0;i<size;i++){
            stb=(stb<<Byte.SIZE) | ((long)val[offset+i] & BYTEMASK);
        }
        return stb;
    }

    public static void main(String[]args){
        byte[]message=new byte[BSZIE+SSZIE+ISZIE+LSZIE];

        int offset = encodeIntBigEndian(message, byteVal, 0, BSZIE);

        System.out.println("Encoded message byte:"+byteArrayToDecimalString(message));

        offset = encodeIntBigEndian(message, shortVal, offset, SSZIE);

        System.out.println("Encoded message short:"+byteArrayToDecimalString(message));

        offset = encodeIntBigEndian(message, intVal, offset, ISZIE);

        System.out.println("Encoded message int:"+byteArrayToDecimalString(message));

        encodeIntBigEndian(message,longVal,offset,LSZIE);

        System.out.println("Encoded message long:"+byteArrayToDecimalString(message));

        long val = decodeIntBigEndian(message, BSZIE, SSZIE);

        System.out.println("decoded short="+val);

        val=decodeIntBigEndian(message,BSZIE+SSZIE+ISZIE,LSZIE);

        System.out.println("decoded long="+val);

        offset=4;

        val=decodeIntBigEndian(message,offset,BSZIE);

        System.out.println("Decoded value(offset" + offset+",size"+BSZIE+")="+val);

        byte byteVal=(byte)decodeIntBigEndian(message,offset,BSZIE);


        System.out.println("same value as byte="+byteVal);
    }
}

