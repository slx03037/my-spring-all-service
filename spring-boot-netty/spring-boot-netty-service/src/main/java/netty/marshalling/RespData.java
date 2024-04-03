package netty.marshalling;

import lombok.Data;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * @author shenlx
 * @description
 * @date 2024/2/27 14:21
 */
@Data
public class RespData implements Serializable {
    private static final long serialVersionUID = 259008367579313851L;
    private String id;
    private String name;
    private Integer age;

    @Override
    public String toString() {
        return new StringJoiner(", ", RespData.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("name='" + name + "'")
                .add("age=" + age)
                .toString();
    }
}
