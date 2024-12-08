package com.puremadeleine.viewith.resolver;

import com.puremadeleine.viewith.dto.member.MemberInfo;
import com.puremadeleine.viewith.exception.ViewithErrorCode;
import com.puremadeleine.viewith.exception.ViewithException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MemberInfoArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        MethodParameter nestedParameter = parameter.nestedIfOptional();
        return nestedParameter.getParameterType().equals(MemberInfo.class)
                || nestedParameter.getNestedParameterType().equals(MemberInfo.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Optional<MemberInfo> memberInfoOptional = findMemberInfo();

        if (isOptional(parameter)) {
            return memberInfoOptional;
        }

        //TODO: throw new ViewithException(ViewithErrorCode.INVALID_TOKEN) 로 변환
        // new AuthenticationCredentialsNotFoundException("Not Foune MemberInfo"));
        return memberInfoOptional.orElseThrow(() -> new ViewithException(ViewithErrorCode.INVALID_TOKEN));
    }

    private Optional<MemberInfo> findMemberInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Optional.ofNullable(authentication)
                .map(auth -> (MemberInfo) auth.getDetails())
                .map(memberInfo -> memberInfo.updateNickname(authentication.getName()))
                .stream().findAny();
    }

    private boolean isOptional(MethodParameter parameter) {
        return parameter.getParameterType() == Optional.class;
    }
}
