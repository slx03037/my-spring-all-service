package com.shenlx.xinwen.springbootadminserversecurity;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class SpringBootAdminServerSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAdminServerSecurityApplication.class, args);
    }

}
