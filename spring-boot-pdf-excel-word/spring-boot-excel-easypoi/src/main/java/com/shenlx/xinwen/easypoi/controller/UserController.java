package com.shenlx.xinwen.easypoi.controller;

import com.shenlx.xinwen.easypoi.model.response.ResponseResult;
import com.shenlx.xinwen.easypoi.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-07-11 11:27
 **/

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/excel/upload")
    public ResponseResult<String> upload(@RequestParam(value = "file", required = true) MultipartFile file) {
        try {
            userService.upload(file.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail(e.getMessage());
        }
        return ResponseResult.success();
    }

    @GetMapping("/excel/download")
    public void download(HttpServletResponse response) {
        try {
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition",
                    "attachment;filename=user_excel_" + System.currentTimeMillis() + ".xlsx");
            userService.downloadExcel(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/excel/fill")
    public void fillTemplate(HttpServletResponse response) {
        try {
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition",
                    "attachment;filename=user_excel_template_" + System.currentTimeMillis() + ".xlsx");
            userService.fillExcelTemplate(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
