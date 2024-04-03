package demo.sendandreceivedata.outandinstream.service;

import demo.sendandreceivedata.outandinstream.VoteMsg;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shenlx
 * @description
 * @date 2024/1/19 9:55
 */
public class VoteService {
    /**创建候选人ID与选票数量得映射*/
    private final Map<Integer,Long> results=new HashMap<Integer,Long>();

    public VoteMsg HandlerRequest(VoteMsg msg){
        if(msg.isResponse()){
            return msg;
        }

        msg.setResponse(true);

        int candidateID = msg.getCandidateID();

        Long count = results.get(candidateID);

        if(count==null){
            count=0L;
        }

        if(!msg.isInquiry()){
            results.put(candidateID,++count);
        }

        msg.setVoteCount(count);

        return msg;
    }


}
