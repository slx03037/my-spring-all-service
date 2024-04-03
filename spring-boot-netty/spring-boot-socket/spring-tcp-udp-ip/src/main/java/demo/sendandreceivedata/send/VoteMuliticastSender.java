package demo.sendandreceivedata.send;

import demo.sendandreceivedata.outandinstream.VoteMsg;
import demo.sendandreceivedata.outandinstream.VoteMsgTextCoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

/**
 * @author shenlx
 * @description
 * @date 2024/1/19 15:24
 */
public class VoteMuliticastSender {
    public static final int CANDIDATEID = 475;

    private static final int TTL=100;

    public static void main(String[]args)throws IOException {
        InetAddress byName = InetAddress.getByName("224.0.0.1");


        int port=8989;

        MulticastSocket socket = new MulticastSocket();

        socket.setTimeToLive(TTL);

        VoteMsgTextCoder coder = new VoteMsgTextCoder();

        VoteMsg voteMsg = new VoteMsg(false, true, CANDIDATEID, 1000001L);

        byte[] msg = coder.towire(voteMsg);

        DatagramPacket packet = new DatagramPacket(msg, msg.length, byName, port);

        System.out.println("sending Text-Ebcoded Request("+msg.length+"bytes);");

        System.out.println(voteMsg);

        socket.send(packet);

        socket.close();
    }
}
