package com.puremadeleine.viewith.controller;

import com.puremadeleine.viewith.dto.common.SortType;
import com.puremadeleine.viewith.dto.member.JoinResDto;
import com.puremadeleine.viewith.dto.member.MemberInfo;
import com.puremadeleine.viewith.dto.member.ProfileResDto;
import com.puremadeleine.viewith.dto.member.RefreshReqDto;
import com.puremadeleine.viewith.dto.member.RefreshResDto;
import com.puremadeleine.viewith.dto.member.UpdateNicknameReqDto;
import com.puremadeleine.viewith.dto.member.ValidateNicknameResDto;
import com.puremadeleine.viewith.dto.review.request.ReviewListReqDto;
import com.puremadeleine.viewith.dto.review.response.ReviewListResDto;
import com.puremadeleine.viewith.service.MemberService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/members")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MemberController {

    MemberService memberService;

    @PostMapping(path = "/login/KAKAO")
    public JoinResDto loginByKakao(@RequestParam(name = "access_token") String accessToken,
                                   @RequestParam(name = "refresh_token") String refreshToken) {
        return memberService.loginByKakao(accessToken, refreshToken);
    }

    @PostMapping(path = "/login/APPLE")
    public JoinResDto loginByApple(@RequestParam(name = "auth_code") String authCode,
                                   @RequestParam(name = "id_token") String idToken) {
        return memberService.loginByApple(authCode, idToken);
    }

    @PutMapping(path = "/refresh")
    public RefreshResDto refresh(@RequestBody RefreshReqDto refreshReqDto) {
        return memberService.refresh(refreshReqDto);
    }

    @DeleteMapping(path = "")
    public void withdraw(MemberInfo memberInfo) {
        memberService.withdraw(memberInfo);
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
    
    @GetMapping(path = "/reviews")
    public ReviewListResDto getMyReviews(MemberInfo memberInfo,
                                         @RequestParam(value = "page", required = false, defaultValue = "1") @Min(1) Integer page,
                                         @RequestParam(value = "size", required = false, defaultValue = "10") @Min(1) @Max(20) Integer size,
                                         @RequestParam(value = "sort_type", required = false, defaultValue = "LATEST") SortType sortType,
                                         @RequestParam(value = "is_summary", required = false, defaultValue = "false") Boolean isSummary) {
        ReviewListReqDto req = ReviewListReqDto.builder()
                .page(page)
                .size(size)
                .sortType(sortType)
                .build();
        return memberService.getMyReviews(memberInfo, req, isSummary);
    }
}