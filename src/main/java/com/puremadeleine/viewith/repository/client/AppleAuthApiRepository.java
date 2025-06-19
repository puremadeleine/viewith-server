package com.puremadeleine.viewith.repository.client;

import com.puremadeleine.viewith.config.client.FeignLoggerConfig;
import com.puremadeleine.viewith.dto.client.AppleJwtKeyDto;
import com.puremadeleine.viewith.dto.client.UpdateTokenResDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "apple-api", url = "${apple.oauth.uri}", configuration = FeignLoggerConfig.class)
public interface AppleAuthApiRepository {
    
    @GetMapping(value = "/auth/keys")
    AppleJwtKeyDto getKey();

    @PostMapping(value = "/auth/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @Headers("Content-Type: application/x-www-form-urlencoded")
    UpdateTokenResDto generateAndValidationToken(@RequestBody Map<String, ?> form);

    @PostMapping(value = "/auth/revoke", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @Headers("Content-Type: application/x-www-form-urlencoded")
    void revoke(@RequestBody Map<String, ?> form);
}
