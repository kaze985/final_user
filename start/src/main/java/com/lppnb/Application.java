package com.lppnb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot Starter
 *
 * @author Frank Zhang
 */
@SpringBootApplication(scanBasePackages = {"com.lppnb", "com.alibaba.cola"})
@MapperScan("com.lppnb.gatewayimpl.database")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
