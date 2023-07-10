package com.shenlx.xinwen.pdf.freeSpirePdf;

import com.spire.pdf.*;
import com.spire.pdf.graphics.PdfFont;
import com.spire.pdf.graphics.PdfFontFamily;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-07-10 15:38
 **/
@RestController
@RequestMapping("file")
@Slf4j
public class FreeSpirePdfWatermarkController {
    @GetMapping("freeSpire")
    public void createPdfWord(HttpServletResponse response) throws IOException {
// 读取原始 PDF 文件
        PdfDocument pdf = new PdfDocument();
        pdf.loadFromFile("original.pdf");

        // 遍历 PDF 中的所有页面
        for (int i = 0; i < pdf.getPages().getCount(); i++) {
            PdfPageBase page = pdf.getPages().get(i);

            page.createTemplate();
            // 添加文本水印
//            PdfWatermark watermark = new PdfWatermark("Watermark");
//            watermark.setFont(new PdfFont(PdfFontFamily.Helvetica, 36));
//            watermark.setOpacity(0.5f);
//            page.getWatermarks().add(watermark);

            // 添加图片水印
            // PdfWatermark watermark = new PdfWatermark("watermark.png");
            // watermark.setOpacity(0.5f);
            // page.getWatermarks().add(watermark);
        }

        // 保存修改后的 PDF 文件
        pdf.saveToFile("output.pdf");
        pdf.close();

    }
}
