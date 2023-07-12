package com.shenlx.xinwen.report.model;

import com.deepoove.poi.data.NumbericRenderData;
import com.deepoove.poi.data.TextRenderData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.tuple.Pair;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat;

import java.util.List;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-07-12 22:52
 **/

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class ListRenderData extends LabelData{
    /**
     * 列表数据集
     */
    private List<TextRenderData> list;

    /**
     * 列表样式,支持罗马字符、有序无序等,默认为点
     */
    private Pair<STNumberFormat.Enum, String> pair = NumbericRenderData.FMT_BULLET;
}
