package com.shenlx.xinwen.springbootvalidation.handler;

import com.fasterxml.jackson.databind.util.EnumValues;
import com.shenlx.xinwen.springbootvalidation.aspect.Validate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.Set;

/**
 * @program: my-spring-all-service
 * @description: 自定义校验器
 * @author: shenlx
 * @create: 2023-04-14 16:39
 **/

public class ConstraintValidatorHandler implements ConstraintValidator<Validate,Integer> {
    /**
     * @Constraint 注解指定了校验器为 EnumValuesConstraintValidator ，因此需要自定义一个。
     * 自定义校验器需要实现 ConstraintValidator<A extends Annotation, T> 这个接口，第一个泛型是 校验注
     * 解 ，第二个是 参数类型
     */

    /**
     *  注意如果约束注解需要对其他数据类型进行校验，则可以的自定义对应数据类型的校验器，然后在约束
     *  注解头上的 @Constraint 注解中指定其他的校验器。
     */

    /**
     * 存储枚举的值
     */
    private Set<Integer> ints=new HashSet<>();
    /**
     * 初始化方法
     * @param enumValues 校验的注解
     */
    @Override
    public void initialize(Validate enumValues) {
        for (int value : enumValues.values()) {
            ints.add(value);
        }
    }

    /**
     *
     * @param value 入参传的值
     * @param context
     * @return
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
//判断是否包含这个值
        return ints.contains(value);
    }
}
