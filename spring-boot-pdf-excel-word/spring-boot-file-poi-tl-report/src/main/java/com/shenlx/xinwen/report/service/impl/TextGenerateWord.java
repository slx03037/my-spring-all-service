package com.shenlx.xinwen.report.service.impl;

import com.shenlx.xinwen.report.config.GenerateWordFactory;
import com.shenlx.xinwen.report.enums.WordContentTypeEnum;
import com.shenlx.xinwen.report.model.LabelData;
import com.shenlx.xinwen.report.model.TextContentData;
import com.shenlx.xinwen.report.service.GenerateWord;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * @program: my-spring-all-service
 * @description: 文本封装
 * @author: shenlx
 * @create: 2023-07-12 22:45
 **/

@Component
public class TextGenerateWord implements GenerateWord {

    @PostConstruct
    public void init(){
        GenerateWordFactory.register(WordContentTypeEnum.TEXT,this);
    }

    @Override
    public Object generateWord(LabelData data) {
        TextContentData contentData = (TextContentData) data;
        return Objects.nonNull(contentData.getLinkData()) ? contentData.getLinkData() :
                Objects.nonNull(contentData.getRenderData()) ? contentData.getRenderData() : contentData.getContent();
    }
}
