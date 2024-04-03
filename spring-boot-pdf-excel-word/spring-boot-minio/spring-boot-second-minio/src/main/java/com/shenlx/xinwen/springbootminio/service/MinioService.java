package com.shenlx.xinwen.springbootminio.service;

import com.shenlx.xinwen.springbootminio.config.MinioProperties;
import com.shenlx.xinwen.springbootminio.model.ResultData;
import io.minio.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-06-25 10:09
 **/
@Slf4j
@Service
public class MinioService {
    @Autowired
    private MinioProperties minioProperties;
    @Autowired
    private MinioClient minioClient;

    public String uploadFile(MultipartFile file) {
        String url="";
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new RuntimeException();
        }
        String bucketName = minioProperties.getBucketName();
        InputStream inputStream = null;

        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient
                        .makeBucket(MakeBucketArgs.builder()
                                .bucket(bucketName).build());
            }

            String datePath = LocalDate.now()
                    .format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String extension = FilenameUtils.getExtension(fileName);
            fileName = datePath + "/" + uuid + "." + extension;
            String objectName = "/" + bucketName + "/" + fileName;
            inputStream = file.getInputStream();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .contentType(file.getContentType())
                            .object(fileName)
                            .stream(inputStream, inputStream.available(), -1)
                            .build());
            url = minioClient
                    .getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                            .method(Method.PUT)
                            .bucket(bucketName)
                            .object(fileName)
                            .expiry(1, TimeUnit.DAYS)
                            .build());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.debug("inputStream close IOException:" + e.getMessage());
                }
            }
        }
        return url;
    }

}
