package com.puremadeleine.viewith.service;

import com.puremadeleine.viewith.config.auth.JwtProperties;
import com.puremadeleine.viewith.domain.member.MemberEntity;
import com.puremadeleine.viewith.dto.member.MemberInfo;
import com.puremadeleine.viewith.provider.MemberProvider;
import com.puremadeleine.viewith.util.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtService {
    static long ACCESS_TOKEN_EXPIRED_MS = TimeUnit.HOURS.toMillis(6);
    static long REFRESH_TOKEN_EXPIRED_MS = TimeUnit.DAYS.toMillis(30);

    JwtProperties jwtProperties;
    MemberProvider memberProvider;

    public String makeAccessToken(MemberInfo memberInfo) {
        return JwtUtil.createToken(jwtProperties.getAccessSecretKey(), ACCESS_TOKEN_EXPIRED_MS, memberInfo);
    }

    public String makeRefreshToken(MemberInfo memberInfo) {
        return JwtUtil.createToken(jwtProperties.getRefreshSecretKey(), REFRESH_TOKEN_EXPIRED_MS, memberInfo);
    }

    public Authentication getAuthentication(String accessToken) {
        MemberInfo memberInfo = getMemberInfo(accessToken);
        Optional<MemberEntity> member = memberProvider.findActiveMember(memberInfo.getMemberId());
        UserDetails userDetails = member.map(m -> User.builder()
                        .username(m.getNickname())
                        .password("")
                        .authorities(List.of())
                        .build())
                .orElse(null);
        if (userDetails == null) {
            throw new AuthenticationCredentialsNotFoundException("Invalid UserDetail");
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, accessToken, userDetails.getAuthorities());
        authentication.setDetails(memberInfo);
        return authentication;
    }

    public MemberInfo getMemberInfo(String accessToken) {
        return JwtUtil.getMemberInfo(accessToken, jwtProperties.getAccessSecretKey());
    }

    public boolean validateAccessToken(String accessToken) {
        return JwtUtil.validateToken(accessToken, jwtProperties.getAccessSecretKey());
    }

    public boolean isExpiredAccessToken(String accessToken) {
        return JwtUtil.isExpiredToken(accessToken, jwtProperties.getAccessSecretKey());
    }

}
