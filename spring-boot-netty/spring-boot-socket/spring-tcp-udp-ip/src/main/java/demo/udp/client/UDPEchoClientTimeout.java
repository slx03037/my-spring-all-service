package demo.udp.client;

import java.io.IOException;
import java.net.*;

/**
 * @author shenlx
 * @description
 * @date 2024/1/16 17:02
 */
public class UDPEchoClientTimeout {

    private static final int timeout=3000;
    private static final int maxitries=5;

    private static final String sendMst="最近过的怎么样";

    private static final int port=8089;

    public static void main(String[]args) throws IOException {
        InetAddress byName = InetAddress.getByName("127.0.0.1");
        DatagramSocket socket = new DatagramSocket();
        /**
         * 设置套接字超时时间
         * 数据报文套接字的超时时间，用来控制嗲用receive()方法的嘴上阻塞时间(毫秒)
         * */
        socket.setSoTimeout(timeout);
        /**创建发送的数据报文*/
        byte[] bytes = sendMst.getBytes();

        DatagramPacket packet = new DatagramPacket(bytes,bytes.length,byName,port);

        /**创建接受的数据报文*/
        DatagramPacket packet1 = new DatagramPacket(new byte[bytes.length], bytes.length);

        int tries=0;

        boolean receiveResponse=false;

        do{
            /**发送数据报文*/
            socket.send(packet);
            try{
                /**处理数据报文的接受*/
                socket.receive(packet1);
                if(!packet1.getAddress().equals(byName)){
                    throw new IOException("received packet from an unknow source");
                }
                receiveResponse=true;
            }catch (Exception e){
                tries+=1;
                System.out.println("time out，"+(maxitries-tries)+"more tries");
            }
        }while(!receiveResponse && tries<maxitries);
        if(receiveResponse){
            System.out.println("receive:"+new String(packet1.getData()));
        }else {
            System.out.println("no response--giving up");
        }
        socket.close();

    }
}
