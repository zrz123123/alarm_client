package com.zz.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.zz.controller","com.zz.service"})
@MapperScan(basePackages = {"com.zz.dao"})
public class AlarmApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlarmApplication.class, args);
    }
}