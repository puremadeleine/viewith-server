package com.puremadeleine.viewith.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "kakao.oauth")
public class KakaoOAuthConfig {
    String redirectUri;
    String clientId;
    String clientSecret;
}
