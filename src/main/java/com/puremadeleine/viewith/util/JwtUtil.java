package com.puremadeleine.viewith.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.puremadeleine.viewith.dto.client.AppleJwtKeyDto;
import com.puremadeleine.viewith.dto.member.MemberInfo;
import com.puremadeleine.viewith.exception.ViewithErrorCode;
import com.puremadeleine.viewith.exception.ViewithException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@UtilityClass
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String createToken(String secretKey, Long expiredMs, MemberInfo memberInfo) {
        Claims claims = Jwts.claims();
        claims.put("member", memberInfo);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(makeKey(secretKey))
                .compact();
    }

    public static MemberInfo getMemberInfo(String token, String secretKey) {
        Claims claims = getClaims(token, secretKey);
        LinkedHashMap<String, Object> memberInfoMap = (LinkedHashMap<String, Object>) claims.get("member");
        return objectMapper.convertValue(memberInfoMap, MemberInfo.class);
    }

    public static boolean validateToken(String token, String secretKey) {
        try {
            findClaims(token, secretKey);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public static boolean isExpiredToken(String token, String secretKey) {
        Claims claims = getClaims(token, secretKey);
        return claims.getExpiration().before(new Date());
    }

    private static Optional<Claims> findClaims(@Nullable String token, String secretKey) {
        return Optional.ofNullable(token)
                .map(tk -> getClaims(token, secretKey));
    }

    // HMAC 서명 방식
    private static Claims getClaims(String token, String secretKey) {
        return Jwts.parserBuilder()
                .deserializeJsonWith(new JacksonDeserializer<>(objectMapper))
                .setSigningKey(makeKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private static SecretKey makeKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public Claims getClaimsBy(AppleJwtKeyDto appleKey, String identityToken) {
        try {
            // 1. JWT 헤더(Base64로 인코딩된 첫 번째 파트) 추출
            String encodedHeader = identityToken.substring(0, identityToken.indexOf("."));
            Map<String, String> header = new ObjectMapper()
                    .readValue(new String(Base64.getDecoder().decode(encodedHeader), StandardCharsets.UTF_8), Map.class);

            // 2. JWK 목록 중 헤더의 kid, alg가 일치하는 키 선택
            AppleJwtKeyDto.AppleJwtKey key = appleKey.getMatchedKeyBy(header.get("kid"), header.get("alg"))
                    .orElseThrow(() -> new ViewithException(ViewithErrorCode.INVALID_TOKEN));

            // 3. 공개키 복원
            PublicKey publicKey = toRSAPublicKey(key.getN(), key.getE());

            // 4. JWT 파싱 및 검증
            return validateAndParseAppleToken(identityToken, publicKey);
        } catch (Exception e) {
            throw new ViewithException(ViewithErrorCode.INVALID_TOKEN);
        }
    }
    
    // RS256 서명 방식
    public Claims validateAndParseAppleToken(String idToken, PublicKey publicKey) {
        try {
            // BouncyCastle 프로바이더 등록 (중복 등록 방지)
            if (Security.getProvider("BC") == null) {
                Security.addProvider(new BouncyCastleProvider());
            }

            String[] parts = idToken.split("\\.");
            if (parts.length != 3) {
                throw new ViewithException(ViewithErrorCode.INVALID_OAUTH_TOKEN);
            }

            String header = parts[0];
            String payload = parts[1];
            String signatureBase64 = parts[2];

            byte[] signature = Base64.getUrlDecoder().decode(signatureBase64);
            String signedData = header + "." + payload;
            if (signature.length == 255) {
                byte[] paddedSignature = new byte[256];
                System.arraycopy(signature, 0, paddedSignature, 1, 255); // 앞에 0 채워넣기
                signature = paddedSignature;
            }
            // BouncyCastle로 서명 검증
            Signature sig = Signature.getInstance("SHA256withRSA", "BC");
            sig.initVerify(publicKey);
            sig.update(signedData.getBytes(StandardCharsets.UTF_8));
            if (!sig.verify(signature)) {
                throw new ViewithException(ViewithErrorCode.INVALID_OAUTH_TOKEN);
            }

            // payload JSON 파싱
            String payloadJson = new String(Base64.getUrlDecoder().decode(payload), StandardCharsets.UTF_8);
            return new DefaultClaims(new ObjectMapper().readValue(payloadJson, Map.class));

        } catch (Exception e) {
            throw new ViewithException(ViewithErrorCode.INVALID_OAUTH_TOKEN);
        }
    }

    public PublicKey toRSAPublicKey(String n, String e) throws Exception {
        byte[] modulusBytes = Base64.getUrlDecoder().decode(n);
        byte[] exponentBytes = Base64.getUrlDecoder().decode(e);

        BigInteger modulus = new BigInteger(1, modulusBytes);
        BigInteger exponent = new BigInteger(1, exponentBytes);

        RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);
        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }
}
