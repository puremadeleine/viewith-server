package com.puremadeleine.viewith.repository.client;

import com.puremadeleine.viewith.config.client.FeignLoggerConfig;
import com.puremadeleine.viewith.dto.client.UpdateTokenResDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "apple-api", url = "${kakao.oauth.uri}", configuration = FeignLoggerConfig.class)
public interface AppleAuthApiRepository {
    String GRANT_TYPE_KEY = "grant_type";
    String CLIENT_ID_KEY = "client_id";
    String CLIENT_SECRET_KEY = "client_secret";
    String REFRESH_TOKEN_KEY = "refresh_token";

    @PostMapping(value = "/auth/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    UpdateTokenResDto refresh(@RequestParam(GRANT_TYPE_KEY) String grantType,
                              @RequestParam(CLIENT_ID_KEY) String clientId,
                              @RequestParam(CLIENT_SECRET_KEY) String clientSecret,
                              @RequestParam(REFRESH_TOKEN_KEY) String refreshToken);
}
