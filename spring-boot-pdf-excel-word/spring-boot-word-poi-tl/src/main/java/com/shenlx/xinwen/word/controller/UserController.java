package com.shenlx.xinwen.word.controller;

import com.deepoove.poi.XWPFTemplate;
import com.shenlx.xinwen.word.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-07-11 10:14
 **/

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/word/download")
    public void download(HttpServletResponse response) {
        try {
            XWPFTemplate document = userService.generateWordXWPFTemplate();
            response.reset();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition",
                    "attachment;filename=user_word_" + System.currentTimeMillis() + ".docx");
            OutputStream os = response.getOutputStream();
            document.write(os);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/word/downloadMD")
    public void downloadMD(HttpServletResponse response) {
        try {
            XWPFTemplate document = userService.generateWordXWPFTemplateMD();
            response.reset();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition",
                    "attachment;filename=user_word_" + System.currentTimeMillis() + ".docx");
            OutputStream os = response.getOutputStream();
            document.write(os);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
