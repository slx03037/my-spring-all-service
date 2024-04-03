package demo.udp.server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * @author shenlx
 * @description
 * @date 2024/1/15 10:06
 */
public class Receive extends JFrame implements Runnable, ActionListener {
    //端口
    int port;

    //广播组地址
    InetAddress group=null;
    //多点广播套接字对象
    MulticastSocket socket=null;

    JButton inceBtn=new JButton("开始接受");
    JButton stopBtn=new JButton("停止接受");

    JTextArea inceAr=new JTextArea(10,10);
    JTextArea inced=new JTextArea(10,10);

    Thread thread;

    Boolean stop =false;

    public Receive(){
        setTitle("广播数据报");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        thread=new Thread(this);
        /**绑定按钮ice的单击世界*/
        inceBtn.addActionListener(this);
        /**绑定按钮stop的单击事件*/
        stopBtn.addActionListener(this);
        /**指定文本域中文字的颜色*/
        inceAr.setForeground(Color.BLUE);
        JPanel north= new JPanel();
        /**按钮添加到面板north上*/
        north.add(inceBtn);
        north.add(stopBtn);
       /**将north防止在窗体的上部*/
        add(north,BorderLayout.NORTH);
        /**创建面板对象center*/
        JPanel center= new JPanel();
        /**设置面板布局*/
        center.setLayout(new GridLayout(1,2));
        /**将文本域添加到面板上*/
        center.add(inceAr);
        center.add(inced);
        add(center,BorderLayout.CENTER);
        validate();
        port=9890;
        try{
            group=InetAddress.getByName("224.0.0.1");
            socket=new MulticastSocket(port);
            socket.joinGroup(group);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setBounds(100,50,360,380);
        setVisible(true);
    }

    @Override
    public void run() {
        while(!stop){
            byte[] data = new byte[1024];
            DatagramPacket packet=null;
            packet=new DatagramPacket(data,data.length,group,port);
            try{
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());
                inceAr.setText("正在接受的内容：\n"+message);
                inced.append(message+"\n");
            } catch (IOException e) {
               e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==inceBtn){
            inceBtn.setBackground(Color.green);
            stopBtn.setBackground(Color.red);
            if(!(thread.isAlive())){
                thread =new Thread(this);
            }
            thread.start();
            stop=false;
        }
        if(e.getSource()==stopBtn){
            inceBtn.setBackground(Color.red);
            stopBtn.setBackground(Color.green);
            stop=true;
        }
    }

    public static void main(String[]args){
        new Receive().setSize(460,200);
    }

}
