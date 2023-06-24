package com.shenlx.xinwen.springbootminio.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-06-23 17:17
 **/
@Slf4j
@Data
public class ResultData <T> implements Serializable {
    private static final long serialVersionUID = 2336055302038508343L;
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回的的数据
     */
    private T data;

    /**
     * 描述
     */
    private String msg;


    public static <T> ResultData<T> success() {
        return new ResultData<>(ResultEnum.SUCCESS.getCode(), null, "操作成功");
    }

    /**
     * 成功请求
     * code : 200
     * msg : 默认 ""
     */
    public static <T> ResultData<T> success(T data) {
        return new ResultData<>(ResultEnum.SUCCESS.getCode(), data, "操作成功");
    }

    public static <T> ResultData<T> success(Integer code, T data) {
        return new ResultData<>(code, data, "操作失败");
    }

    public static <T> ResultData<T> error(ResultEnum exceptionEnum) {
        return new ResultData<>(exceptionEnum.getCode(), null, exceptionEnum.getMsg());
    }

    public static <T> ResultData<T> error(Integer code, String msg) {
        return new ResultData<>(code, null, msg);
    }

    public static <T> ResultData<T> error(String msg) {
        return new ResultData<>(ResultEnum.ERROR.getCode(), null, msg);
    }

    private ResultData() {
    }

    private ResultData(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }
}
