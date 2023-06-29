package spring.boot.easy.excel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-06-28 11:43
 **/
@SpringBootApplication
@MapperScan("spring.boot.easy.excel.mapper")
public class Application {
    public static void main(String[]args){
        SpringApplication.run(Application.class,args);
    }
}
