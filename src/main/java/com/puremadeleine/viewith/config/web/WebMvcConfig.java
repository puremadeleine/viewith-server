package com.puremadeleine.viewith.config.web;

import com.puremadeleine.viewith.resolver.MemberInfoArgumentResolver;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
//@EnableWebMvc
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebMvcConfig implements WebMvcConfigurer {
    private MemberInfoArgumentResolver memberInfoArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberInfoArgumentResolver);
    }

//    @Override
//    public void addFormatters(FormatterRegistry registry) {
//        registry.addConverter((Converter<?, ?>) OAuthType.OAuthTypeConverter.INSANCE);
//    }
}
