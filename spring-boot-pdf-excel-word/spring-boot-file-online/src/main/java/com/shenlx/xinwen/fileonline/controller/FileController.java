package com.shenlx.xinwen.fileonline.controller;

import com.shenlx.xinwen.fileonline.utils.HttpUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-07-12 22:09
 **/
@RestController
public class FileController {
    @GetMapping(value = "/image/review")
    public ResponseEntity<?> imageReview() throws IOException {
        File file = new File("D:\\workspace-github-project\\my-spring-all-service\\spring-boot-pdf-excel-word\\spring-boot-file-online\\src\\main\\resources\\1.jpeg");
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] buf = new byte[fileInputStream.available()];
            fileInputStream.read(buf);
            return HttpUtil.getResponseEntity(buf, "inline", "1.jpeg");
        }
    }

    @GetMapping(value = "/image/down")
    public ResponseEntity<?> imageDown() throws IOException {
        File file = new File("D:\\workspace-github-project\\my-spring-all-service\\spring-boot-pdf-excel-word\\spring-boot-file-online\\src\\main\\resources\\1.jpeg");
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] buf = new byte[fileInputStream.available()];
            fileInputStream.read(buf);
            return HttpUtil.getResponseEntity(buf, "attachment", "1.jpeg");
        }
    }
}
