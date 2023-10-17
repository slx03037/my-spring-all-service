package com.shenlx.xinwen.pdfToWord.controller;

import com.shenlx.xinwen.pdfToWord.utils.CommonUtil;
import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.io.File;
import java.net.URLEncoder;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-07-15 08:37
 **/
@Slf4j
@RestController
public class FileController {
    @PostMapping("pdfToDoc")
    public void pdfToDoc(@RequestParam("pdfFile") MultipartFile pdfFile, HttpServletResponse response) throws Exception {

        // 将MultiparFile转为File
        File file = CommonUtil.multipartToFile(pdfFile);

        // 创建Pdf工具类对象
        PdfDocument pdf = new PdfDocument();

        // 拼接Word文件名
        String projectPath = System.getProperty("user.dir");
        String name = file.getName();
        pdf.loadFromFile(projectPath + "/" + name);

        //保存为Word格式
        String fileName = file.getName().substring(0, file.getName().lastIndexOf(".")) + ".docx";
        pdf.saveToFile(fileName, FileFormat.DOCX);

        // 将问文件转为字节流返回供前端下载
        File wordFile = new File(fileName);

        String path="D:\\workspace-github-project\\my-spring-all-service\\spring-boot-pdf-excel-word\\spring-boot-pdf-word\\src\\main\\resources\\file";
        String s = path + "\\"+ fileName;
        log.info("文件：{}",s);
        File uploadFile=new File(s);
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");

        // 下载文件能正常显示中文
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

        // 实现文件下载
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos=null;

        try {
            fis = new FileInputStream(wordFile);
            bis = new BufferedInputStream(fis);
            fos= new FileOutputStream(uploadFile);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                fos.write(buffer);
                i = bis.read(buffer);
            }
            log.info("word文件成功下载");

        } catch (Exception e) {
            log.info("word文件下载失败");
        } finally {
            if (bis != null) {
                try {
                    // 结束后关闭文件流
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    // 结束后关闭文件流
                    fos.close();
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 最后删除转换过程中生成的文件
            wordFile.delete();
            file.delete();
        }
    }



}
