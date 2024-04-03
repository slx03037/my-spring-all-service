package demo.tcp.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @author shenlx
 * @description
 * @date 2024/1/12 13:24
 */

/**
 * tcp服务端
 * 1。创建一个ServerSocket是咧并指定本地端口，此套接字的功能是侦听该指定端口的连接
 * 2.重复执行
 *  a.调用ServerSocket的accept()方法以获得下一个客户端连接，基于新建的客户端连接，创建一个Socket实例，并由accept()方法返回
 *  b.使用所返回的Socket实例的InputStream和OutPutStream与客户端进行通信
 *  c.通信完成后，使用Socket类的close()方法关闭该客户端套接字连接
 */

public class TCPEchoServer {
    //SIZE of receive buffer
    private static final int BUFSIZE=32;

    public static void main1(String[]args) throws IllegalAccessException, IOException {
        System.out.println(args.length);
        int port=8080;
        if(args !=null && args.length>0){
            try{
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
                System.out.println("---");
            }
        }
        //int serverPort = Integer.parseInt(args[0]);

        ServerSocket serverSocket = new ServerSocket(port);
        /**创建服务器端套接字*/
        byte[]receiveBuffer=new byte[BUFSIZE];

        int receiveMsgSize;
        /**永久循环，迭代出处理新的连接请求*/
        while(true){
            /**接受新的阿连接请求
             * serverSocket实例地唯一目的，是为了新地tcp连接请求提供一个新地已连接地Socket实例
             * ，当服务器端的已经准备号处理客户端请求时，就调用accept()方法该方法阻塞等待
             * ，知道有向ServerSocket实例指定端口的新的连接请求到来()如果新的连接请求到来时，在服务器端的套接字刚创建，而尚未调用accept()，
             * 那么新的连接将排在队列中，在这种情况下调用accept()方法，将立即得到响应，即立即返回客户端套接字。
             * */
            Socket accept = serverSocket.accept();

            /**报告已连接的客户端
             *在新的创建Socket实例中，我们可以查询所连接的客户端的相应地址和端口号
             * Socket类的getRemoteSocketAddress()方法返回一个包含了客户端地址和端口号的 remoteSocketAddress实例
             * remoteSocketAddress类的toString()方法以“/<地址>:<端口号>”的形式打印出来这些信息
             */
            SocketAddress remoteSocketAddress = accept.getRemoteSocketAddress();
            /**获取套接字的输入输出流
             * 写入这个服务器端套接字的OutputStream的字节信息将从客户端套接字的InputStream中读出
             * ，而写入客户端OutputStream的字节信息将从服务器端套接字的InputStream读出
             */
            InputStream in = accept.getInputStream();

            OutputStream out = accept.getOutputStream();
            /**接受并复制数据，知道客户端关闭
             * while循环从输入流中返回读取字节数据(在数据可获得时)，并立即将同样的字节返回给输出流，这个过程一直持续到客户端关闭连接
             * InputStream的read()方法每次获取缓存数组所能放下的最多的字节（receiveMsgSize），并存入数组（receiveBuffer），
             * 同事返回实际读取的字节数。read()方法将堵塞等待，知道有可读数据。在接受的字节数与其发送字节树相等时就关闭连接。
             * 因此在服务端最终将从read()方法中收到为-1的返回值
             */
            while((receiveMsgSize=in.read(receiveBuffer))!=-1){
                out.write(receiveBuffer,0,receiveMsgSize);
            }
            accept.close();
        }
    }

    private static final int port=8080;
    private ServerSocket serverSocket;
    private Socket socket;
    void start(){
        try{
        /**启动服务器
         * 服务器启用8080端口
         * */
        serverSocket = new ServerSocket(port);
        System.out.println("服务器套接字已经创建成功");
            while (true) {
                System.out.println("等待客户机的连接");
                /**
                 * 服务器监听客户机连接
                 * */
                socket = serverSocket.accept();
                /**根绝套机子流创建字符流*/
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (true) { /**循环接受信息**/
                    /**读取一行文本*/
                    String message = reader.readLine();
                    if ("exit".equals(message)) {
                        System.out.println("客户机退出");
                        break;
                    }
                    System.out.println("客户机：" + message);
                }
                reader.close();
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[]args) throws IOException {
        TCPEchoServer tcpEchoServer = new TCPEchoServer();
        tcpEchoServer.start();
    }
}
