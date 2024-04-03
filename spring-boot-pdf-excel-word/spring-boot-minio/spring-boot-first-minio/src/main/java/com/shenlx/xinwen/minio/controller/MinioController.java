package com.shenlx.xinwen.minio.controller;

import com.shenlx.xinwen.minio.model.ResultData;
import com.shenlx.xinwen.minio.model.UploadResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-06-24 21:24
 **/
@RestController
@Slf4j
public class MinioController {
    /**
     * @author: xx
     * @date: 2022/5/25 15:32
     * @description: 上传文件
     */
    @PostMapping("/upload")
    public ResultData minioUpload(@RequestParam(value = "file") MultipartFile file){
        UploadResponse response = null;
        try {
           // response = minioUtil.uploadFile(file, "bucket01");
        } catch (Exception e) {
            log.error("上传失败",e);
            return ResultData.error(e.getMessage());
        }
        return ResultData.success(response);
    }
}
