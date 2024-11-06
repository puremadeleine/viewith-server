package com.puremadeleine.viewith.controller;

import com.puremadeleine.viewith.dto.member.*;
import com.puremadeleine.viewith.service.MemberService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        memberService.putNickname(memberInfo, nickname.getNickname());
    }

    @GetMapping(path = "/nicknames/validate")
    public ValidateNicknameResDto validateNickname(@RequestParam(name = "name") String nickname) {
        return memberService.validateNickname(nickname);
    }

    @GetMapping(path = "/bookmarks")
    public Object getBookmarks(MemberInfo memberInfo) {
        return new Object();
    }
}