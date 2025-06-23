package com.puremadeleine.viewith.config.client;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplePrivateKey {

    String clientSecretKey;

    public ApplePrivateKey(final AppleOAuthProperties appleOAuthProperties) {
        this.clientSecretKey = appleOAuthProperties.getClientSecretKey();
    }

    @Bean
    public PrivateKey appleOAuthPrivateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] encoded = Base64.getDecoder().decode(clientSecretKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        return keyFactory.generatePrivate(keySpec);
    }
}
