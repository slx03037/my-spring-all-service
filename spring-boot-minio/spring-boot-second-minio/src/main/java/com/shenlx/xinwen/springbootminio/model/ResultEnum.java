package com.shenlx.xinwen.springbootminio.model;

public enum ResultEnum implements EnumInterface{
    SUCCESS(200, "操作成功"),
    ERROR(500, "操作失败"),
    ;
    ResultEnum(Integer code, String msg) {
	this.code = code;
	this.msg = msg;
    }

    private Integer code;

    private String msg;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
