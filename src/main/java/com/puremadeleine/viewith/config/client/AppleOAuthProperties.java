package com.puremadeleine.viewith.config.client;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@ConfigurationProperties(prefix = "apple.oauth")
public class AppleOAuthProperties {
    String clientId;
    String loginKey;
    String teamId;
    String clientSecretKey;
    String audience;
}