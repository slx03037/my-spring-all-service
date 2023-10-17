package com.shenlx.xinwen.pdfToWord.controller;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;
import com.spire.pdf.graphics.PdfImage;
import com.spire.pdf.graphics.PdfTilingBrush;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-07-15 12:35
 **/

@RestController
public class PdfRemoveWarningController {
    @GetMapping("createPdf")
    public void PdfRemovingWaterMark() throws IOException, DocumentException {
//        Document document = new Document();
////空白文档，用来将版权信息打印到这上面
//        document.loadFromFile("E:/temp.docx");
////真实文档 fhadmin.cn
//        document.insertTextFromFile("E:/test.doc",FileFormat.PDF);
////输出为PDF
//        document.saveToFile("E:/test.pdf", FileFormat.PDF);
        PdfReader reader = new PdfReader("D:\\workspace-github-project\\my-spring-all-service\\spring-boot-pdf-excel-word\\spring-boot-pdf-word\\src\\main\\resources\\exchange\\沈立鑫.pdf");
//删除带版权信息的第一页，并重新输出为PDF fhadmin.cn
        List<String> pages = new ArrayList<>();
        for(int i = 2;i <= reader.getNumberOfPages();i++){
            pages.add(String.valueOf(i));
        }
        reader.selectPages(StringUtils.join(pages, ','));
        String s="D:\\workspace-github-project\\my-spring-all-service\\spring-boot-pdf-excel-word\\spring-boot-pdf-word\\src\\main\\resources\\exchange\\沈立鑫s.pdf";
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(s));
        stamp.close();
        reader.close();
    }

    @GetMapping("updatePdf")
    public  void updatePdf() {
        String pdfPath="D:\\workspace-github-project\\my-spring-all-service\\spring-boot-pdf-excel-word\\spring-boot-pdf-word\\src\\main\\resources\\exchange\\沈立鑫.pdf";
        //创建PdfDocument对象,并加载PDF测试文档
        PdfDocument pdf = new PdfDocument();
        pdf.loadFromFile(pdfPath);

        //添加一个空白页，目的为了删除jar包添加的水印，后面再移除这一页
        pdf.getPages().add();

        int rows = 3;  //指定一行有多少个图片
        int col = 3; //指定一个pdf页有多少行图片组
        //遍历文档每一页,加载图片，并设置成平铺背景（水印）
        for (int i = 0; i < pdf.getPages().getCount();i++) {
            PdfPageBase page = pdf.getPages().get(i);

            Dimension2D dimension2D = new Dimension();
            dimension2D.setSize(page.getCanvas().getSize().getWidth()/rows, page.getCanvas().getSize().getHeight()/col);

            PdfTilingBrush brush = new PdfTilingBrush(dimension2D);
            brush.getGraphics().setTransparency(0.2f); //透明度
            brush.getGraphics().translateTransform(brush.getSize().getWidth()/10,brush.getSize().getHeight()/10);//坐标位置
            //brush.getGraphics().rotateTransform(30);//选转角度

            PdfImage image = PdfImage.fromImage("D:\\workspace-github-project\\my-spring-all-service\\spring-boot-pdf-excel-word\\spring-boot-pdf-word\\src\\main\\resources\\1.jpeg");
            brush.getGraphics().drawImage(image,(brush.getSize().getWidth()-image.getWidth())/rows,(brush.getSize().getHeight())/col);

            Rectangle2D rectangle2D = new Rectangle2D.Float();
            rectangle2D.setFrame(new Point(0,0),page.getCanvas().getClientSize());

            page.getCanvas().drawRectangle(brush,rectangle2D);
        }

        //移除第一个页
        pdf.getPages().remove(pdf.getPages().get(pdf.getPages().getCount()-1));
        String s="D:\\workspace-github-project\\my-spring-all-service\\spring-boot-pdf-excel-word\\spring-boot-pdf-word\\src\\main\\resources\\exchange\\沈立鑫s.pdf";
        //保存文档
        pdf.saveToFile(s);
        pdf.dispose();
    }
}
