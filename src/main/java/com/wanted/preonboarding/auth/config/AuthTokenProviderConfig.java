package com.wanted.preonboarding.auth.config;

import com.wanted.preonboarding.auth.AuthTokenProvider;
import com.wanted.preonboarding.infra.config.jwt.JwtConfig;
import com.wanted.preonboarding.module.user.service.UserFindService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthTokenProviderConfig {

    @Bean
    public AuthTokenProvider authTokenProvider(JwtConfig jwtConfig, UserFindService userFindService) {
        return new AuthTokenProvider(jwtConfig, userFindService);
    }
}
