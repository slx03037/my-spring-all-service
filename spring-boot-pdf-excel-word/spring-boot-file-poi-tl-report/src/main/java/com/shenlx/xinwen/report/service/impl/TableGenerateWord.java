package com.shenlx.xinwen.report.service.impl;

import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.shenlx.xinwen.report.config.GenerateWordFactory;
import com.shenlx.xinwen.report.enums.WordContentTypeEnum;
import com.shenlx.xinwen.report.model.LabelData;
import com.shenlx.xinwen.report.model.TableSeriesRenderData;
import com.shenlx.xinwen.report.service.GenerateWord;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: my-spring-all-service
 * @description:  列封装类
 * @author: shenlx
 * @create: 2023-07-12 22:51
 **/

@Component
public class TableGenerateWord implements GenerateWord {
    @PostConstruct
    private void init(){
        GenerateWordFactory.register(WordContentTypeEnum.TABLE,this);
    }
    @Override
    public Object generateWord(LabelData data) {
        TableSeriesRenderData tableData = (TableSeriesRenderData) data;
        RowRenderData header = RowRenderData.build(tableData.getHeader());
        List<RowRenderData> contentData = new ArrayList<>();
        tableData.getContents().forEach(con ->{
            contentData.add(RowRenderData.build(con));
        });
        return new MiniTableRenderData(header,contentData);
    }
}
