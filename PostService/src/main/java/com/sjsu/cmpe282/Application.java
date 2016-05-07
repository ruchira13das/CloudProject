package com.sjsu.cmpe282;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.sjsu.cmpe282")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}