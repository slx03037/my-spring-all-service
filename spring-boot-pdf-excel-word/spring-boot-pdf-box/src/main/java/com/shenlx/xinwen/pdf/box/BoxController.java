package com.shenlx.xinwen.pdf.box;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-07-10 10:32
 **/
@RestController
@RequestMapping("pdf")
public class BoxController {
    @PostMapping("box")
    public void createPdf(@RequestParam("file") MultipartFile file,HttpServletResponse response) throws IOException {
        String uploadPath = "D:\\workspace-github-project\\my-spring-all-service\\spring-boot-pdf-excel-word\\spring-boot-pdf-box\\src\\main\\resources\\file"; // 改这里
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        //log.info(uploadDir.getAbsolutePath());

        // 本地文件
        File localFile = new File(uploadPath + File.separator + file.getOriginalFilename());

        // transfer to local
        file.transferTo(localFile);
        //File files= new File(file.getName());
        PDDocument document = PDDocument.load(localFile);
        // 遍历 PDF 中的所有页面
        for (int i = 0; i < document.getNumberOfPages(); i++) {
            PDPage page = document.getPage(i);
            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);

            // 设置字体和字号
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 36);

            // 设置透明度
            contentStream.setNonStrokingColor(200, 200, 200);

            // 添加文本水印
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 100); // 设置水印位置
            contentStream.showText("Watermark"); // 设置水印内容
            contentStream.endText();
            contentStream.close();
        }
        document.save(localFile);
        document.close();
        response.reset();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition",
                "attachment;filename=file_" + System.currentTimeMillis() + ".pdf");

        // 从文件读到servlet response输出流中
        // File file = new File("D:\\workspace-github-project\\my-spring-all-service\\spring-boot-pdf-excel-word\\spring-boot-download-upload\\src\\main\\resources\\file\\号卡资源.txt"); // 改这里
        try (FileInputStream inputStream = new FileInputStream(localFile);) { // try-with-resources
            byte[] b = new byte[1024];
            int len;
            while ((len = inputStream.read(b)) > 0) {
                response.getOutputStream().write(b, 0, len);
            }
        }
    }
}
