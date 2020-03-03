package com.lzy.miaosha;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lzy.miaosha.dao")
public class MainAppliacation {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(MainAppliacation.class,args);
    }
}