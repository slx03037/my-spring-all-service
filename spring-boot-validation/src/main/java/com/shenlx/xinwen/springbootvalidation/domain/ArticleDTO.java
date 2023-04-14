package com.shenlx.xinwen.springbootvalidation.domain;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-04-12 17:23
 **/
@Data
public class ArticleDTO {
    @NotNull( message= "文章id不能为空")
    @Min(value = 1,message = "文章ID不能为负数")
    private Integer id;
    @NotBlank(message = "文章内容不能为空")
    private String content;
    @NotBlank(message = "作者Id不能为空")
    private String authorId;
    @Future(message = "提交时间不能为过去时间")
    private Date submitTime;

}
