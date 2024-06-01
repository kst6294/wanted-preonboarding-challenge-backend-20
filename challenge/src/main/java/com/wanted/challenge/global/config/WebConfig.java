package com.wanted.challenge.global.config;

import com.wanted.challenge.global.auth.AuthUserResolver;
import com.wanted.challenge.global.auth.OptionalAuthUserResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final AuthUserResolver authUserResolver;
    private final OptionalAuthUserResolver optionalAuthUserResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authUserResolver);
        resolvers.add(optionalAuthUserResolver);
    }
}
