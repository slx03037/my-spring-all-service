package com.shenlx.xinwen.report.model;

import com.deepoove.poi.data.HyperLinkTextRenderData;
import com.deepoove.poi.data.TextRenderData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @program: my-spring-all-service
 * @description: 创建文本实体
 * @author: shenlx
 * @create: 2023-07-12 22:44
 **/

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class TextContentData extends LabelData {

    /**
     * 纯文本内容
     */
    private String content;
    /**
     * 带样式文本
     */
    private TextRenderData renderData;
    /**
     * 超链接文本
     */
    private HyperLinkTextRenderData linkData;
}
