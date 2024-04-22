package netty.agreement.exmaple.demo2;

import lombok.Data;

/**
 * @author shenlx
 * @description
 * @date 2024/4/3 17:41
 */

public enum ResultType {
    FAIL((byte)0,"认证失败")
    ,SUCCESS((byte)1,"认证成功");

    private final byte value;

    private final String msg;

    ResultType(byte value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public byte value() {
        return value;
    }

    public byte getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }
}
