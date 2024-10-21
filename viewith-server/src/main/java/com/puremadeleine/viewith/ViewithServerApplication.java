package com.puremadeleine.viewith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ViewithServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ViewithServerApplication.class, args);
    }
}
