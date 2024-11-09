package com.puremadeleine.viewith.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController {

    @GetMapping(value = {"health", "health-check", "healthcheck"})
    public String healthCheck() {
        return "ok";
    }
}
