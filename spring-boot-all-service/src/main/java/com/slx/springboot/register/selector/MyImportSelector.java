package com.slx.springboot.register.selector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-17 22:10
 **/

public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{
                "cc.mrbird.demo.domain.Apple",
                "cc.mrbird.demo.domain.Banana",
                "cc.mrbird.demo.domain.Watermelon"
        };
    }
}
