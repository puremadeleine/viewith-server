package com.puremadeleine.viewith.repository.client;

import com.puremadeleine.viewith.config.client.FeignLoggerConfig;
import com.puremadeleine.viewith.dto.client.KakaoUserInfoResDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakao-api", url = "${kakao.api.uri}", configuration = FeignLoggerConfig.class)
public interface KakaoApiRepository {
    @GetMapping(value = "/v2/user/me", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KakaoUserInfoResDto getUserInfo(@RequestHeader("Authorization") String accessToken);

    @PostMapping(value = "/v1/user/unlink")
    void unlink(@RequestHeader("Authorization") String accessTokens);
}
