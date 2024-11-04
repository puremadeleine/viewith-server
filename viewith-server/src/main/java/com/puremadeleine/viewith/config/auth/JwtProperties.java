package com.puremadeleine.viewith.config.auth;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    String accessSecretKey;
    String refreshSecretKey;
}