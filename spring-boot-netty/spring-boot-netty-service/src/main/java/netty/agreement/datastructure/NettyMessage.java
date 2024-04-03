package netty.agreement.datastructure;



import java.util.StringJoiner;

/**
 * @author shenlx
 * @description
 * @date 2024/3/28 15:26
 */
public class NettyMessage {
    /**消息头*/
    private Header header;

    /**消息体*/
    private Object body;

    public final Header getHeader() {
        return header;
    }

    public final void setHeader(Header header) {
        this.header = header;
    }

    public final Object getBody() {
        return body;
    }

    public final void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NettyMessage.class.getSimpleName() + "[", "]")
                .add("header=" + header)
                .add("body=" + body)
                .toString();
    }
}
