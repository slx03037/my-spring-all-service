/**
 * @description
 * @author shenlx
 * @date 2024/1/15 17:29
 */
package demo.udp;

/**
 * 发送数据步骤
 * 1.使用DatagramSocket()创建一个数据包套接字
 * 2.使用DatagramPacket(byte[] buf,int offset,int length,InetAddress address,int port)创建要发送的数据包
 * 3.使用DatagramSocket类的send()方法发送数据包
 *
 * 接受数据包步骤
 * 1.使用DatagramSocket(int port)创建数据包套接字，绑定到指定的端口
 * 2.使用DatagramPacket(byte[]buf,int length)创建字节数组来接受数据包
 * 3.使用DatagramPacket类的receive()方法接受UDP包
 *
 * 注意DatagramSocket类的receive()方法接受数据时，如果还没有可以接受的数据，在正常情况下receive()方法将阻塞，一直等到网络上数据传来，
 * receive()方法也没有阻塞，肯定是程序有问题，大多数情况下是因为使用了一个被其他程序占用的端口号
 */