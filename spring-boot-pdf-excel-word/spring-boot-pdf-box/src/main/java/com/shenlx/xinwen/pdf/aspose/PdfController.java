package com.shenlx.xinwen.pdf.aspose;

import com.aspose.pdf.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-07-10 15:41
 **/
@RestController
@RequestMapping("/file/pdf")
public class PdfController {
    @PostMapping("/addTextWatermark")
    public ResponseEntity<byte[]> addTextWatermark(@RequestParam("file") MultipartFile file) throws IOException {
        // 加载 PDF 文件
        Document pdfDocument = new Document(file.getInputStream());
        TextStamp textStamp = new TextStamp("Watermark");
        textStamp.setWordWrap(true);
        textStamp.setVerticalAlignment(VerticalAlignment.Center);
        textStamp.setHorizontalAlignment(HorizontalAlignment.Center);
        pdfDocument.getPages().get_Item(1).addStamp(textStamp);

        // 保存 PDF 文件
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        pdfDocument.save(outputStream);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"watermarked.pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(outputStream.toByteArray());
    }

    @PostMapping("/addImageWatermark")
    public ResponseEntity<byte[]> addImageWatermark(@RequestParam("file") MultipartFile file) throws IOException {
        // 加载 PDF 文件
        Document pdfDocument = new Document(file.getInputStream());
        ImageStamp imageStamp = new ImageStamp("watermark.png");
        imageStamp.setWidth(100);
        imageStamp.setHeight(100);
        imageStamp.setVerticalAlignment(VerticalAlignment.Center);
        imageStamp.setHorizontalAlignment(HorizontalAlignment.Center);
        pdfDocument.getPages().get_Item(1).addStamp(imageStamp);

        // 保存 PDF 文件
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        pdfDocument.save(outputStream);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"watermarked.pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(outputStream.toByteArray());
    }

}
