package com.shenlx.xinwen.springbootasync.async;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-12 20:31
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
