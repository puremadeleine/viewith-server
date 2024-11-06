package com.puremadeleine.viewith.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.puremadeleine.viewith.dto.member.MemberInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.LinkedHashMap;

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
            getClaims(token, secretKey);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public static boolean isExpiredToken(String token, String secretKey) {
        Claims claims = getClaims(token, secretKey);
        return claims.getExpiration().before(new Date());
    }

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
}
