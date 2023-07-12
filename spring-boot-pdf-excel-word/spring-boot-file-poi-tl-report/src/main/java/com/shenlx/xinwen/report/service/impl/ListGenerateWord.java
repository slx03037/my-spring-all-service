package com.shenlx.xinwen.report.service.impl;

import com.deepoove.poi.data.NumbericRenderData;
import com.shenlx.xinwen.report.config.GenerateWordFactory;
import com.shenlx.xinwen.report.enums.WordContentTypeEnum;
import com.shenlx.xinwen.report.model.LabelData;
import com.shenlx.xinwen.report.model.ListRenderData;
import com.shenlx.xinwen.report.service.GenerateWord;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-07-12 22:54
 **/

@Component
public class ListGenerateWord implements GenerateWord {
    @PostConstruct
    private void init(){
        GenerateWordFactory.register(WordContentTypeEnum.LIST,this);
    }
    @Override
    public Object generateWord(LabelData data) {
        ListRenderData listData =  (ListRenderData) data;
        return new NumbericRenderData(listData.getPair(),listData.getList());
    }
}
