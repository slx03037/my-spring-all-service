package netty.marshalling;

import lombok.Data;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * @author shenlx
 * @description
 * @date 2024/2/27 14:20
 */
@Data
public class ReqData implements Serializable {
    private static final long serialVersionUID = -1054499372522281796L;
    private String id;
    private String name;
    private Integer age;

    @Override
    public String toString() {
        return new StringJoiner(", ", ReqData.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("name='" + name + "'")
                .add("age=" + age)
                .toString();
    }
}
