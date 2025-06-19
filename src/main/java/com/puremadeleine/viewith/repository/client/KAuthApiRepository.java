package com.puremadeleine.viewith.repository.client;

import com.puremadeleine.viewith.config.client.FeignLoggerConfig;
import com.puremadeleine.viewith.dto.client.UpdateTokenResDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "kauth-api", url = "${kakao.oauth.uri}", configuration = FeignLoggerConfig.class)
public interface KAuthApiRepository {
    @PostMapping(value = "/oauth/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @Headers("Content-Type: application/x-www-form-urlencoded")
    UpdateTokenResDto refresh(@RequestBody Map<String, ?> form);
}
