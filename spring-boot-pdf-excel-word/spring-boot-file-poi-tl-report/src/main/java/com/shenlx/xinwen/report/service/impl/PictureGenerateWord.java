package com.shenlx.xinwen.report.service.impl;

import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.util.BytePictureUtils;
import com.shenlx.xinwen.report.config.GenerateWordFactory;
import com.shenlx.xinwen.report.enums.WordContentTypeEnum;
import com.shenlx.xinwen.report.model.LabelData;
import com.shenlx.xinwen.report.model.PictureContentData;
import com.shenlx.xinwen.report.service.GenerateWord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @program: my-spring-all-service
 * @description: 图片封装
 * @author: shenlx
 * @create: 2023-07-12 22:47
 **/

@Component
public class PictureGenerateWord implements GenerateWord {

    @PostConstruct
    private void init(){
        GenerateWordFactory.register(WordContentTypeEnum.PICTURE,this);
    }

    @Override
    public Object generateWord(LabelData data) {
        PictureContentData picture = (PictureContentData) data;
        return StringUtils.isNotBlank(picture.getPicUrl()) ? new PictureRenderData(picture.getWidth(),picture.getHeight(),picture.getPicType().getPicName(),
                BytePictureUtils.getUrlBufferedImage(picture.getPicUrl()))
                : new PictureRenderData(picture.getWidth(),picture.getHeight(),picture.getFile());
    }
}
