package demo.vote;

import demo.sendandreceivedata.outandinstream.VoteMsg;
import demo.sendandreceivedata.outandinstream.VoteMsgCoder;
import demo.sendandreceivedata.outandinstream.VoteMsgTextCoder;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * @author shenlx
 * @description
 * @date 2024/2/4 16:02
 */
public class voteMulticastSender {
    public static final int CANDIDATEID=475;

    private final static int TTL=10000;

    public static void main(String[] args) throws IOException {

        InetAddress destAddr = InetAddress.getByName("8191");

        MulticastSocket socket=new MulticastSocket();

        socket.setTimeToLive(TTL);

        VoteMsgCoder coder=new VoteMsgTextCoder();

        VoteMsg voteMsg = new VoteMsg(true, true, CANDIDATEID, 1000001L);

        byte[] msg = coder.towire(voteMsg);

        DatagramPacket packet = new DatagramPacket(msg, msg.length, destAddr, 8191);

        System.out.println("Sending text-encoded Request("+msg.length+"bytes)");

        System.out.println(voteMsg);

        socket.send(packet);

        socket.close();

    }
}
