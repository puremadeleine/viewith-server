package com.puremadeleine.viewith.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MemberInfoArgumentResolver implements HandlerMethodArgumentResolver {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // MemberInfo || Optional<MemberInfo>
        return MemberInfo.class.isAssignableFrom(parameter.getParameterType())
                ||
                (
                        Optional.class.isAssignableFrom(parameter.getParameterType())
                                && isOptionalOfMemberInfo(parameter.getGenericParameterType())
                );

    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 미인증
        boolean notAuth = authentication == null || !authentication.isAuthenticated() || !MemberInfo.class.isAssignableFrom(authentication.getDetails().getClass());
        if (notAuth) {
            if (Optional.class.isAssignableFrom(parameter.getParameterType())) {
                return Optional.empty();
            } else {
                throw new ViewithException(ViewithErrorCode.INVALID_TOKEN);
            }
        }

        MemberInfo memberInfo = (MemberInfo) authentication.getDetails();
        memberInfo = memberInfo.updateNickname(authentication.getName());

        if (MemberInfo.class.isAssignableFrom(parameter.getParameterType())) {
            return memberInfo;
        } else {
            return Optional.of(memberInfo);
        }
    }

    private boolean isOptionalOfMemberInfo(Type type) {
        if (type instanceof ParameterizedType parameterizedType) {
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            return actualTypeArguments.length == 1 && MemberInfo.class.isAssignableFrom((Class<?>) actualTypeArguments[0]);
        }
        return false;
    }
}
