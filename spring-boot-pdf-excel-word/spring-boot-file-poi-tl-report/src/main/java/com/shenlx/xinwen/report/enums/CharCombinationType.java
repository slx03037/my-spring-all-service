package com.shenlx.xinwen.report.enums;

/**
 * 图表系列类型
 */
public enum CharCombinationType {
    /**
     * 多组合
     */
    MULTI("Multi"),
    /**
     * 单图形
     */
    Single("Single");

    private final String type;

    CharCombinationType(String type){
        this.type = type;
    }
    public String getType(){
        return type;
    }
}
