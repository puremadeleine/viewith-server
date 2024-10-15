package com.puremadeleine.viewith.repository.client;

import com.puremadeleine.viewith.config.FeignLoggerConfig;
import com.puremadeleine.viewith.dto.client.AccessTokenResDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakao-oauth", url = "${kakao.oauth.uri}", configuration = FeignLoggerConfig.class)
public interface KakaoOAuthRepository {
    @PostMapping(value="/oauth/token", consumes = "application/x-www-form-urlencoded")
    AccessTokenResDto getAccessToken(@RequestParam("grant_type") String grantType,
                                     @RequestParam("client_id")String clientId,
                                     @RequestParam("request_uri")String redirectUri,
                                     @RequestParam("code")String code,
                                     @RequestParam("client_secret")String clientSecret);
}
