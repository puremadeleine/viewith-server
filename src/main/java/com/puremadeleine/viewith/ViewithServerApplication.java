package com.puremadeleine.viewith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients
@ConfigurationPropertiesScan(basePackages = {"com.puremadeleine.viewith.config"})
@SpringBootApplication
@EnableScheduling
public class ViewithServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ViewithServerApplication.class, args);
    }
}
