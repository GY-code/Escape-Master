package com.team6.escapemaster_server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.team6.escapemaster_server.mapper")
@SpringBootApplication
public class EscapeMasterServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EscapeMasterServerApplication.class, args);
    }

}
