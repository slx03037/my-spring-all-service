package com.shenlx.xinwen.springbootvalidation.aspect;

import com.shenlx.xinwen.springbootvalidation.handler.ConstraintValidatorHandler;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = { ConstraintValidatorHandler.class})
@Target(value = { ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(value = RetentionPolicy.RUNTIME)
@NotNull(message = "不能为空")
public @interface Validate {
    /**
     * 根据 Bean Validation API 规范的要求有如下三个属性是必须的：
     * 1. message ：定义消息模板，校验失败时输出
     * 2. groups ：用于校验分组
     * 3. payload ： Bean Validation API 的使用者可以通过此属性来给约束条件指定严重级别. 这个属性
     * 并不被API自身所使用。
     * 除了以上三个必须要的属性，添加了一个 values 属性用来接收限制的范围。
     * 该校验注解头上标注的如下一行代码：
     * 这个 @Constraint 注解指定了通过哪个校验器去校验。
     */

    /**
     * 提示消息
     */
    String message() default "传入的值不在范围内";
    /**
     * 分组
     * @return
     */
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    /**
     * 可以传入的值
     * @return
     */
    int[] values() default { };

}
