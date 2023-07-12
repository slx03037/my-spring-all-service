package com.shenlx.xinwen.fileonline.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-07-12 22:08
 **/
@Component
@Slf4j
public class HttpUtil {
    public static ResponseEntity<?> getResponseEntity(byte[] fileContent, String contentDispositionType, String originalFileName){
        ResponseEntity.BodyBuilder responseEntity = ResponseEntity.ok();
        HttpHeaders httpHeaders = new HttpHeaders();
        Tika tika = new Tika();
        String mediaType = tika.detect(fileContent);
        httpHeaders.setContentType(MediaType.parseMediaType(mediaType));
        httpHeaders.setContentDisposition(ContentDisposition.builder(contentDispositionType)
                .filename(URLEncoder.encode(originalFileName)).build());
        httpHeaders.setCacheControl(CacheControl.noCache());
        //httpHeaders.setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES));
        return responseEntity.headers(httpHeaders).body(fileContent);
    }
}
