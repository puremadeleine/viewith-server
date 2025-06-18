package com.puremadeleine.viewith.dto.client;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.Optional;

@Getter
@Jacksonized
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppleJwtKeyDto {
    private List<AppleJwtKey> keys;

    @Getter
    @Jacksonized
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static class AppleJwtKey {
        private String kty;  // Key Type (e.g., RSA)
        private String kid;  // Key ID
        private String use;  // Public Key Use (e.g., sig)
        private String alg;  // Algorithm (e.g., RS256)
        private String n;    // Modulus (Base64URL-encoded)
        private String e;    // Exponent (Base64URL-encoded)
    }

    public Optional<AppleJwtKeyDto.AppleJwtKey> getMatchedKeyBy(String kid, String alg) {
        return this.keys.stream()
                .filter(key -> key.getKid().equals(kid) && key.getAlg().equals(alg))
                .findFirst();
    }
}
