package com.shenlx.xinwen.pdfToWord.controller;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-07-15 12:14
 **/
@RestController
public class WordToPdfController {
    @GetMapping("wordToPdf")
    public void wordToPdf() throws Exception {
        String fileName = "D:\\workspace-github-project\\my-spring-all-service\\spring-boot-pdf-excel-word\\spring-boot-pdf-word\\src\\main\\resources\\file\\沈立鑫.docx";
        String pdfPath="D:\\workspace-github-project\\my-spring-all-service\\spring-boot-pdf-excel-word\\spring-boot-pdf-word\\src\\main\\resources\\file\\prefix_沈立鑫(1).pdf";

        String fileNameA = "D:\\workspace-github-project\\my-spring-all-service\\spring-boot-pdf-excel-word\\spring-boot-pdf-word\\src\\main\\resources\\exchange\\沈立鑫.pdf";
        String pdfPathA="D:\\workspace-github-project\\my-spring-all-service\\spring-boot-pdf-excel-word\\spring-boot-pdf-word\\src\\main\\resources\\exchange\\prefix_沈立鑫(1).pdf";
        Document doc = new Document(fileName);
        //创建ToPdfParameterList类的对象
        //ToPdfParameterList parameterList = new ToPdfParameterList();

        //设置PDF文档的一致性级别
        //parameterList.setPdfConformanceLevel(PdfConformanceLevel.Pdf_A_1_A);

        //将文档保存为PDF/A格式
        doc.saveToFile(fileNameA, FileFormat.PDF);

        // doc.saveToFile(fileNameA); //将Word保存为SVG格式
        // PdfDocument  pdf = new PdfDocument(pdfPath);
        // pdf.saveToFile(pdfPathA,FileFormat.DOCX);
        /**
         *  BufferedImage image = doc.saveToImages( 0, ImageType.Bitmap);
         *         File file = new File("ToPNG.png");
         *         ImageIO.write(image, "PNG", file); //将Word转为PDF
         *         doc.saveToFile("Word转PDF.pdf", FileFormat.PDF); //将Word保存为SVG格式
         *         doc.saveToFile("ToSVG.svg",FileFormat.SVG); //将Word保存为RTF格式
         *         doc.saveToFile("ToRTF.rtf",FileFormat.Rtf); //将Word保存为XPS格式
         *         doc.saveToFile("ToXPS.xps",FileFormat.XPS); //将Word保存为XML格式
         *         doc.saveToFile("ToXML.xml",FileFormat.Xml); //将Word保存为TXT格式
         *         doc.saveToFile("ToTXT.txt",FileFormat.Txt);
         *
         */
    }
}
