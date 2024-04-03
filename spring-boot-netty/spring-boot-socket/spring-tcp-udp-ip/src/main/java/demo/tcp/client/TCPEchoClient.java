package demo.tcp.client;

/**
 * @author shenlx
 * @description
 * @date 2024/1/12 10:49
 */


import java.io.*;
import java.net.Socket;
import java.net.SocketException;

/**
 * tcp客户端向服务器发起连接请求后，就被动地等待服务器地响应，典型地tcp客户端要经过下面三步
 * 1.闯将一个Socket实例，构造函数向指定地远程和服务端口建立一个tcp连接
 * 2，连接套接字地输入输出流(I/0 Streams) 进行通信，一个socket连接实例包括一个InputStream和一个OutputStream，它们地用法同于其他java输入输出流
 * 3.使用Socket类地close()方法关闭连接
 *
 */
public class TCPEchoClient  {
    public static void main1(String[]args) throws  IOException {
        int port=8080;
        if(args !=null && args.length>0){
            try{
                port =Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
                System.out.println("++++");
            }
        }
//        if((args.length<2)||(args.length>3)){
//            throw new IllegalAccessException("Parameter(s):<Sever><Word>[<Port>]");
//        }
        /**server name or ip address*/
        //String server = args[0];
        /**convert argument String to bytes using the default character encoding*/
        String message="客户端请求连接";
        /**转换回馈字符集*/
        byte[]data=message.getBytes();
        /**create socket that is connected to server on specified port*/
        /**确定回馈服务器地端口号*/
        Socket socket=new Socket("127.0.0.1",port);
        System.out.println("Conneted to serve...sending echo STRING");
        /***创建tcp套接字*/
        /**send the encoded string to the server*/
        /***
         * 获取套接字地输入输出流
         */
        InputStream in =socket.getInputStream();
        /**发送字符串到回馈服务器*/
        OutputStream out = socket.getOutputStream();
        out.write(data);
        /**Receive the same string back from the server*/
        /**Total bytes received so far*/
        int totalyBetesRcvd=0;
        /**Bytes received in last read*/
        int bytesRcvd;
        /**从回馈服务器接受回馈信息*/
        while(totalyBetesRcvd<data.length){
            if((bytesRcvd= in.read(data,totalyBetesRcvd,data.length-totalyBetesRcvd))==-1) {
                throw new SocketException("Connection closed prematurely");
            }
            totalyBetesRcvd = totalyBetesRcvd + bytesRcvd;
        }
        System.out.println("Received: "+new String(data));
        socket.close();
    }
    /**根绝套机子字节流创建的字符输出流*/
    //private PrintWriter writer;
    /**客户端套接字*/
    //Socket socket;
    /**展示信息的文本域*/
//    private JTextArea area=new JTextArea();
//    /**发送信息的文本框*/
//    private JTextField text=new JTextField();


//    public TCPEchoClient(){
//        setTitle("向服务器发送数据");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        /**主容器*/
//        Container contentPane = getContentPane();
//        /**滚动面板*/
//        JScrollPane jScrollPane = new JScrollPane(area);
//        getContentPane().add(jScrollPane,BorderLayout.CENTER);
//        /**将文本框房子啊窗体的下部*/
//        contentPane.add(text,"South");
//        /**文本框触发回车事件*/
//
//        text.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                /**将文本框中的信息显示在文本域之中*/
//                area.append(text.getText()+"\n");
//                /**将文本框中的信息写入流*/
//                writer.println(text.getText().trim());
//                /**将文本框清空*/
//                text.setText("今天天气很好");
//                System.out.println("打印内容："+text.getText());
//            }
//        });
//    }

    private void connect(){
        try{
            Socket socket=new Socket("127.0.0.1",8080);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            while(true){
                for(int i=0;i<=10;i++){
                    writer.println("今天天气怎么样");
                }
                writer.println("exit");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String []args){
        TCPEchoClient tcpEchoClient = new TCPEchoClient();
        /**连接服务器*/
        tcpEchoClient.connect();
    }

}
