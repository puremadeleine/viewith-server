package com.puremadeleine.viewith.repository.client;

import com.puremadeleine.viewith.config.FeignLoggerConfig;
import com.puremadeleine.viewith.dto.client.UserInfoResDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakao-api", url = "${kakao.api.uri}", configuration = FeignLoggerConfig.class)
public interface KakaoApiRepository {
    @GetMapping(value="/v2/user/me", consumes = "application/x-www-form-urlencoded")
    UserInfoResDto getUserInfo(@RequestHeader("Authorization")String accessToken,
                               @RequestParam("secure_resource") Boolean secureResource,
                               @RequestParam("property_keys") String properties);
}
