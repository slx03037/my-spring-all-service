package com.shenlx.xinwen.pdfToWord.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-07-15 08:36
 **/

public class CommonUtil {
    /**
     * MultipartFile 转换成File
     *
     * @param multfile 原文件类型
     * @return File
     */
    public static File multipartToFile(MultipartFile multfile) throws IOException {
        File file = null;
//        file = File.createTempFile("prefix","_" + multfile.getOriginalFilename());
        String projectPath = System.getProperty("user.dir");
        file = new File(projectPath + "/prefix_" + multfile.getOriginalFilename());

        multfile.transferTo(file);
        return file;

    }
}
