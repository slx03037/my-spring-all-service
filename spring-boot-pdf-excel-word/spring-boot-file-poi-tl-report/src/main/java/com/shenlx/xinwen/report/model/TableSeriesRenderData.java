package com.shenlx.xinwen.report.model;

import com.deepoove.poi.data.TextRenderData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-07-12 22:50
 **/

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class TableSeriesRenderData extends LabelData {

    /**
     * 表头 将表头与表格内容进行分开赋值，使其更加清晰。
     */
    private TextRenderData[] header;
    /**
     * 表内容
     */
    private List<TextRenderData[]> contents;
}
