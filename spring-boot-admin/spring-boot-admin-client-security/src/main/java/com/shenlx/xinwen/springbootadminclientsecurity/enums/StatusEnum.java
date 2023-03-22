package com.shenlx.xinwen.springbootadminclientsecurity.enums;

public enum StatusEnum {
      DOWN_STATUS("DOWN","健康检查没通过")
    , OFFLINE_STATUS("OFFLINE","服务离线")
    , UP_STATUS("UP","服务上线")
    , UNKNOWN_STATUS("UNKNOWN","服务异常")
    ;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    StatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;

    private String desc;

    public static StatusEnum getStatus(String code){
        for(StatusEnum e:StatusEnum.values()){
            if(code.equals(e.code)){
                return e;
            }
        }
        return null;
    }
}
