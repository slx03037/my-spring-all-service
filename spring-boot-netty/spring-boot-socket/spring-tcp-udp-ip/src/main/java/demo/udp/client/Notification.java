package demo.udp.client;

/**
 * @author shenlx
 * @description
 * @date 2024/1/15 10:01
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * udp客户端
 * udp客户端首先被动等待联系的服务器端发送一个数据报文
 * 1.创建一个DatagramSocket实例，可以选择对本地地址和端口号进行设置
 * 2.使用DatagramSocket类的send()和receive()方法来发送和接受DatagramPacket实例，进行通信
 * 3.通信完成后，使用DatagramSocket类的close()方法来销毁套接字
 *
 *
 * 1.向服务器端发送回馈字符串
 * 2.在receive()方法上最多等待3秒钟，在超时时间前若没有收到响应，则重发请求(最多5次)
 * 3.终止客户端
 */

public class Notification extends Thread{

    //发送消息
    String weather="节目预报:八点有大型晚会，请收听";
    //Resend timeout(millisenconds)
    private static final int TIMEOUT=3000;
    //Maximum retransmissions
    private static final int MAXTRIES=5;

    //端口
      int port=9890;
    InetAddress address=null;
    //多点广播套接字
    MulticastSocket socket;


    Notification(){
        try{
            /**广播地址*/
            address  = InetAddress.getByName("224.0.0.1");
            /**实例化多点广播套接字*/
            socket=new MulticastSocket(port);
            /**指定发送范围是本地网络*/
            socket.setTimeToLive(1);
            socket.joinGroup(address);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public  void run(){
        while(true){
            /**数据包*/
            DatagramPacket packet=null;
            /**字符串消息的字节数组*/
            byte[] data =weather.getBytes();
            /**将数据打包*/
            packet=new DatagramPacket(data,data.length,address,port);
            /**控制台打印消息*/
            System.out.println(weather);
            try{
                /**发送数据*/
                socket.send(packet);
                /**线程休眠*/
                sleep(3000);
            }catch (IOException e){
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Notification udpEchoClientTimeout= new Notification();
        udpEchoClientTimeout.start();

    }
}
