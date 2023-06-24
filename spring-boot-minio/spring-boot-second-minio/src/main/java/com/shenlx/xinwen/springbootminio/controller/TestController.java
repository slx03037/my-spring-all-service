package com.shenlx.xinwen.springbootminio.controller;

import com.shenlx.xinwen.springbootminio.model.ResultData;
import com.shenlx.xinwen.springbootminio.utils.MinioUtil;
import com.shenlx.xinwen.springbootminio.model.UploadResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-06-23 17:16
 **/
@RestController
@Slf4j
public class TestController {
    @Autowired
    private MinioUtil minioUtil;

    /**
     * @author: xx
     * @date: 2022/5/25 15:32
     * @description: 上传文件
     */
    @PostMapping("/upload")
    public ResultData minioUpload(@RequestParam(value = "file") MultipartFile file){
        UploadResponse response = null;
        try {
            response = minioUtil.uploadFile(file);
        } catch (Exception e) {
            log.error("上传失败",e);
            return ResultData.error(e.getMessage());
        }
        return ResultData.success(response);
    }


    /**
     * @author: xx
     * @date: 2022/5/25 15:32
     * @description: 上传视频
     */
    @PostMapping("/uploadVideo")
    public ResultData uploadVideo(@RequestParam(value = "file") MultipartFile file){
        UploadResponse response = null;
        try {
            response = minioUtil.uploadVideo(file);
        } catch (Exception e) {
            log.error("上传失败",e);
            return ResultData.error(e.getMessage());
        }
        return ResultData.success(response);
    }
}
