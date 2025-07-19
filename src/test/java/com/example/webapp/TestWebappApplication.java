package com.example.webapp;

import org.springframework.boot.SpringApplication;

public class TestWebappApplication {

    public static void main(String[] args) {
        SpringApplication.from(WebappApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
