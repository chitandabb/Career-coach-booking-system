package com.ccbs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ccbs.mapper")
public class CcbsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CcbsApplication.class, args);
    }
}


