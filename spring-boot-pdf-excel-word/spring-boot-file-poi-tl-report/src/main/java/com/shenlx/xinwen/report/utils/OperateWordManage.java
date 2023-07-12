package com.shenlx.xinwen.report.utils;

import com.deepoove.poi.XWPFTemplate;
import com.shenlx.xinwen.report.config.GenerateWordFactory;
import com.shenlx.xinwen.report.model.LabelData;
import com.shenlx.xinwen.report.service.GenerateWord;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-07-12 22:42
 **/
@Slf4j
public class OperateWordManage {
    /**
     * tempFilePath：模板文件的地址。
     *
     * destFilePath：生成后的文件地址。
     *
     * List<LabelData> contents：各标签封装后的数据集合。
     * @param tempFileFile
     * @param destFilePath
     * @param contents
     */
    public static void generateWordContent(File tempFileFile, String destFilePath, List<LabelData> contents){
        FileOutputStream fos = null;
        XWPFTemplate template = null;
        try {
            template = XWPFTemplate.compile(tempFileFile).render(new HashMap<String,Object>(contents.size()){{
                contents.forEach(content ->{
                    GenerateWord backData = GenerateWordFactory.getBackData(content.getTypeEnum());
                    put(content.getLabelName(),backData.generateWord(content));
                });
            }});
            fos = new FileOutputStream(destFilePath);
            template.write(fos);
            fos.flush();
        }catch (Exception e){
            log.error("替换生成图表报错：{}",e.getMessage());
            e.printStackTrace();
        }finally {
            try{
                if (Objects.nonNull(fos)){
                    fos.close();
                }
                if (Objects.nonNull(template)){
                    template.close();
                }
            }catch (Exception e){
                log.error("关闭数据流报错：{}",e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
