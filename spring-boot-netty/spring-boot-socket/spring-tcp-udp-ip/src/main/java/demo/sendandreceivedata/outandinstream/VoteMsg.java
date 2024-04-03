package demo.sendandreceivedata.outandinstream;

/**
 * @author shenlx
 * @description
 * @date 2024/1/18 14:46
 */
public class VoteMsg {

    /**
     * 其值为true时表示该消息时查询请求(为false时标识该消息是投票消息)
     */
    private boolean isInquiry;

    /**指该消息是响应(由服务器发送)还是请求*/
    private boolean isResponse;

    /**
     * 指示了候选人的ID  candidateID的范围是0-1000
     */
    private int candidateID;

    /**
     * 指示出所有查询的候选人获得的总票选数
     * voteCount在响应消息中只能是一个非零值(isResponse为true)
     * voteCount不能为负数
     * */
    private long voteCount;

    public static final int max_candidate_id=1000;

    public VoteMsg(boolean isResponse,boolean isInquiry,int candidateID,long voteCount){
        if(voteCount !=0 && isResponse){
            throw new IllegalArgumentException("Request vote count must be zero");
        }

        if(candidateID<0 || candidateID>max_candidate_id){
            throw new IllegalArgumentException("Bad candidate_id:"+candidateID);
        }

        if(voteCount<0){
            throw new IllegalArgumentException("Total must be >=zero");
        }

        this.candidateID=candidateID;
        this.isResponse=isResponse;
        this.isInquiry=isInquiry;
        this.voteCount=voteCount;
    }

    public boolean isInquiry() {
        return isInquiry;
    }

    public void setInquiry(boolean inquiry) {
        isInquiry = inquiry;
    }

    public boolean isResponse() {
        return isResponse;
    }

    public void setResponse(boolean response) {
        isResponse = response;
    }

    public int getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(int candidateID) {
        if(candidateID<0|| candidateID> max_candidate_id){
            throw new IllegalArgumentException("Bad candidate id:"+candidateID);
        }
        this.candidateID = candidateID;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(long voteCount) {
        if((voteCount !=0 && !isResponse) || voteCount<0){
            throw new IllegalArgumentException("bad vote count :"+voteCount);
        }
        this.voteCount = voteCount;
    }

    @Override
    public String toString() {
        return "VoteMsg{" +
                "isInquiry=" + isInquiry +
                ", isResponse=" + isResponse +
                ", candidateID=" + candidateID +
                ", voteCount=" + voteCount +
                '}';
    }
}
