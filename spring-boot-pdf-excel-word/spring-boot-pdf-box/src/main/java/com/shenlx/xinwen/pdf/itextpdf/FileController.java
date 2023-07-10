package com.shenlx.xinwen.pdf.itextpdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-07-10 11:00
 **/
@RestController
@RequestMapping("file")
@Slf4j
public class FileController {
    private static final String uploadPath = "D:\\workspace-github-project\\my-spring-all-service\\spring-boot-pdf-excel-word\\spring-boot-pdf-box\\src\\main\\resources\\file\\";
    @GetMapping("itexPdf")
    public void createPdfWord(HttpServletResponse response) throws IOException, DocumentException {
        String readier="Spring Boot 进阶笔记.pdf";
        PdfReader reader = new PdfReader(uploadPath+readier);
        FileOutputStream outputStream = new FileOutputStream(uploadPath + "output.pdf");
        PdfStamper stamper = new PdfStamper(reader, outputStream);
        //获取 PDF 中的页数
        int pageCount = reader.getNumberOfPages();
        PdfContentByte contentByte;
        log.info("pdf得页数：{}",pageCount);
        // 添加水印
        for (int i = 1; i <= pageCount; i++) {
            /**
             * 注意一个方法  getOverContent和getUnderContent  如果pdf本身就是图片的内容  使用getUnderContent  时会被原来的内容遮住，看不出水印效果，但是如果是
             * getOverContent 方法可以显示在图片上面，但是被遮住很多内容，打印出来挺难看的
             */
            contentByte = stamper.getOverContent(i);
           // contentByte = stamper.getUnderContent(i); // 或者 getOverContent()
            // 开始
            contentByte.beginText();
            // 设置字体及字号
            contentByte.setFontAndSize(BaseFont.createFont(), 36f);
            // 设置起始位置
            contentByte.setRGBColorFill(200, 200, 200);
            //开始写入水印
            contentByte.showTextAligned(Element.ALIGN_CENTER, "sssssssss2BSssssssssssssss", 300, 400, 0);
            contentByte.endText();
        }
        stamper.close();
        reader.close();

        response.reset();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition",
                "attachment;filename=file_" + System.currentTimeMillis() + ".pdf");
        try (FileInputStream inputStream = new FileInputStream(uploadPath + "output.pdf");) { // try-with-resources
            byte[] b = new byte[1024];
            int len;
            while ((len = inputStream.read(b)) > 0) {
                response.getOutputStream().write(b, 0, len);
            }
        }
    }

    @GetMapping("imagePdf")
    public void createPdfImage(HttpServletResponse response) throws IOException, DocumentException {
        String readier="Spring Boot 进阶笔记.pdf";
        String  imageName="1.jpeg";
        PdfReader reader = new PdfReader(uploadPath+readier);
        FileOutputStream outputStream = new FileOutputStream(uploadPath + "output_image.pdf");
        //ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PdfStamper stamper = new PdfStamper(reader, outputStream);
        String path = uploadPath + imageName;
        Integer x=400;
        Integer y=500;
        Integer width=20;
        Integer height=20;
        // 给每一页添加水印
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            PdfContentByte overContent = stamper.getOverContent(i);
            overContent.beginText();
            // 图片所在位置x
            //图片所在位置y
            //图片宽度
            //图片高度
            // 设置水印对齐方式 水印内容 X坐标 Y坐标 旋转角度
            com.itextpdf.text.Image img = Image.getInstance(path);
            img.setAbsolutePosition(x, y);
            img.scaleAbsolute(width, height);

            overContent.addImage(img);
            // 设置水印颜色
            overContent.setColorFill(BaseColor.GRAY);

            //结束设置
            overContent.endText();
            overContent.stroke();
            //addImage(s, x, y, width, height);
        }
        stamper.close();
        reader.close();

    }

    private static void addImage(PdfContentByte waterMar,String imgpath, float x, float y, float width, float height)
            throws DocumentException, IOException {
        waterMar.beginText();

        // 设置水印对齐方式 水印内容 X坐标 Y坐标 旋转角度
        com.itextpdf.text.Image img = Image.getInstance(imgpath);
        img.setAbsolutePosition(x, y);
        img.scaleAbsolute(width, height);

        waterMar.addImage(img);
        // 设置水印颜色
        waterMar.setColorFill(BaseColor.GRAY);

        //结束设置
        waterMar.endText();
        waterMar.stroke();
    }
}
