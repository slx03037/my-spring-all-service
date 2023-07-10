package com.shenlx.xinwen.file.controller;

import com.shenlx.xinwen.file.model.ResponseResult;
import lombok.extern.slf4j.Slf4j;
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
 * @create: 2023-07-06 15:40
 **/
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {
    @PostMapping("/upload")
    public ResponseResult<String> upload(@RequestParam(value = "file", required = true) MultipartFile file) {
        try {
            // 本地文件保存位置
            String uploadPath = "D:\\workspace-github-project\\my-spring-all-service\\spring-boot-pdf-excel-word\\spring-boot-download-upload\\src\\main\\resources\\file"; // 改这里
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            log.info(uploadDir.getAbsolutePath());

            // 本地文件
            File localFile = new File(uploadPath + File.separator + file.getOriginalFilename());

            // transfer to local
            file.transferTo(localFile);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail(e.getMessage());
        }
        return ResponseResult.success();
    }

    @GetMapping("/download")
    public void download(HttpServletResponse response) {
        response.reset();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition",
                "attachment;filename=file_" + System.currentTimeMillis() + ".txt");

        // 从文件读到servlet response输出流中
        File file = new File("D:\\workspace-github-project\\my-spring-all-service\\spring-boot-pdf-excel-word\\spring-boot-download-upload\\src\\main\\resources\\file\\号卡资源.txt"); // 改这里
        try (FileInputStream inputStream = new FileInputStream(file);) { // try-with-resources
            byte[] b = new byte[1024];
            int len;
            while ((len = inputStream.read(b)) > 0) {
                response.getOutputStream().write(b, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
