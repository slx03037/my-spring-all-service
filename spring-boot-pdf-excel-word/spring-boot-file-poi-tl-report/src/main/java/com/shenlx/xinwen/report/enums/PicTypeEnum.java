package com.shenlx.xinwen.report.enums;

/**
 * @program: my-spring-all-service
 * @description: 图片类型
 * @author: shenlx
 * @create: 2023-07-12 22:47
 **/

public enum  PicTypeEnum {
    /**
     * png图片
     */
    PNG(".png"),
    /**
     * JPG图片
     */
    JPG(".jpg"),
    /**
     * jpeg
     */
    JPEG(".jpeg");

    private final String picName;

    PicTypeEnum(String picName) {
        this.picName = picName;
    }

    public String getPicName() {
        return picName;
    }
}
