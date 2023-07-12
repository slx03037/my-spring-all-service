package com.shenlx.xinwen.report.config;

import com.shenlx.xinwen.report.enums.WordContentTypeEnum;
import com.shenlx.xinwen.report.manage.GenerateWord;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @program: my-spring-all-service
 * @description: 生成word工厂
 * @author: shenlx
 * @create: 2023-07-12 22:41
 **/

public class GenerateWordFactory {
    /**
     * TYPE_BACK_DATA：表示各标签封装数据的类与标签的一一对应。
     *
     * GenerateWord getBackData(WordContentTypeEnum typeEnum) ：根据标签类型获取对应生成的数据。
     */
    private static final Map<WordContentTypeEnum, GenerateWord> TYPE_BACK_DATA = new HashMap<>();

    public static void register(WordContentTypeEnum typeEnum, GenerateWord word){
        if (Objects.nonNull(typeEnum)){
            TYPE_BACK_DATA.put(typeEnum,word);
        }
    }

    public static GenerateWord getBackData(WordContentTypeEnum typeEnum){
        return TYPE_BACK_DATA.get(typeEnum);
    }
}
