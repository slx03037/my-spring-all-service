package demo.sendandreceivedata.outandinstream.client;

import demo.sendandreceivedata.outandinstream.LengthFramer;
import demo.sendandreceivedata.outandinstream.VoteMsg;
import demo.sendandreceivedata.outandinstream.VoteMsgBinCoder;

import java.io.OutputStream;
import java.net.Socket;

/**
 * @author shenlx
 * @description
 * @date 2024/1/19 10:22
 */
public class VoteClientTcp {
    public static final int CANDIDATEID=888;

    private static final String address="127.0.0.1";

    private static final int port=8989;

    public static void main(String[]args)throws Exception{
        Socket socket = new Socket(address, port);

        OutputStream out = socket.getOutputStream();

        VoteMsgBinCoder coder = new VoteMsgBinCoder();

        LengthFramer lengthFramer = new LengthFramer(socket.getInputStream());

        VoteMsg voteMsg = new VoteMsg(false, true, CANDIDATEID, 0);

        byte[] towire = coder.towire(voteMsg);
        
        //send request
        System.out.println("Sending Inquiry("+towire.length+"bytes);");
        
        System.out.println(voteMsg);
        
        lengthFramer.frameMsg(towire,out);
        
        //Now send a vote
        voteMsg.setInquiry(false);

        towire = coder.towire(voteMsg);

        System.out.println("Sending Vote("+towire.length+"bytes);");

        lengthFramer.frameMsg(towire,out);
        
        //Receive inquiry response
        towire = lengthFramer.nextMsg();

        voteMsg= coder.fromWire(towire);

        System.out.println("Sending Response("+towire.length+"bytes);");

        System.out.println(voteMsg);

        //Receive vote Response
         voteMsg= coder.fromWire(lengthFramer.nextMsg());

         System.out.println("Received Response("+towire.length+"bytes);");

         System.out.println(voteMsg);

         socket.close();

    }
}
