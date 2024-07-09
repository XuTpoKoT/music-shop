package com.musicshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.musicshop")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}

