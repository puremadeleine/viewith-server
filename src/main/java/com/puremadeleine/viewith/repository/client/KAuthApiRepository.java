package com.puremadeleine.viewith.repository.client;

import com.puremadeleine.viewith.config.client.FeignLoggerConfig;
import com.puremadeleine.viewith.dto.client.UpdateTokenResDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kauth-api", url = "${kakao.oauth.uri}", configuration = FeignLoggerConfig.class)
public interface KAuthApiRepository {
    String GRANT_TYPE_KEY = "grant_type";
    String CLIENT_ID_KEY = "client_id";
    String REFRESH_TOKEN_KEY = "refresh_token";

    @PostMapping(value = "/oauth/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    UpdateTokenResDto refresh(@RequestParam(GRANT_TYPE_KEY) String grantType,
                              @RequestParam(CLIENT_ID_KEY) String clientId,
                              @RequestParam(REFRESH_TOKEN_KEY) String refreshToken);
}
