package com.puremadeleine.viewith.controller;

import com.puremadeleine.viewith.dto.member.*;
import com.puremadeleine.viewith.service.MemberService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/members")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MemberController {

    MemberService memberService;

    @GetMapping(path = "/login/{auth_type}")
    public JoinResDto login(@PathVariable(name = "auth_type") OAuthType oAuthType,
                            @RequestParam String code) {
        return memberService.login(oAuthType, code);
    }

    @PutMapping(path = "/refresh")
    public Object refresh(@RequestParam(name = "oauth_type") @Validated OAuthType oauthType) {
        return new Object();
    }

    @DeleteMapping(path = "")
    public Object withdraw(@RequestParam(name = "oauth_type") @Validated OAuthType oauthType) {
        return new Object();
    }

    @GetMapping(path = "/profiles")
    public ProfileResDto getProfile(MemberInfo memberInfo) {
        return memberService.getProfile(memberInfo.getMemberId());
    }

    @PutMapping(path = "/nicknames")
    public void putNickname(MemberInfo memberInfo, UpdateNicknameReqDto nickname) {
        // 204
        memberService.putNickname(memberInfo.getMemberId(), nickname.getNickname());
    }

    @GetMapping(path = "/nicknames/duplicate")
    public DuplicateNicknameResDto duplicateNickname(@RequestParam(name = "name") String nickname) {
        return memberService.duplicateNickname(nickname);
    }

    @GetMapping(path = "/bookmarks")
    public Object getBookmarks(MemberInfo memberInfo) {
        return new Object();
    }

    // 인증, MemberInfo -> 파라미터와 상관없이 없으면 무조건 security에서 막힘
    @GetMapping(path = "/test1")
    public MemberInfo test1(MemberInfo memberInfo) {
        return memberInfo;
    }

    // 인증, Optional -> 파라미터와 상관없이 없으면 무조건 security에서 막힘 / 대신 Optional로 조회 가능
    @GetMapping(path = "/test2")
    public MemberInfo test2(Optional<MemberInfo> memberInfo) {
        return memberInfo.orElse(null);
    }

    // 미인증, MemberInfo -> exclude-uris 지만, 없으면 401 Error 발생
    @GetMapping(path = "/test3")
    public MemberInfo test3(MemberInfo memberInfo) {
        return memberInfo;
    }

    // 미인증, MemberInfo -> exclude-uris 지만, 토큰 있으면 Optional<MemberInfo>로 회원 정보 조회 가능
    @GetMapping(path = "/test4")
    public MemberInfo test4(Optional<MemberInfo> memberInfo) {
        return memberInfo.orElse(null);
    }
}