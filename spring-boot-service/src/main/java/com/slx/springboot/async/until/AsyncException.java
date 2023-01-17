package com.slx.springboot.async.until;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-06 22:46
 **/

public class AsyncException extends RuntimeException {
    public AsyncException() {
        super();
    }

    public AsyncException(String msg) {
        super(msg);
    }

    public AsyncException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    /**
     * 错误代码
     */
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
