package com.wanted.preonboarding.infra.config.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class JwtConfig {

    @Value(value = "${token.secret}")
    private String secret;

    @Value("${token.expiration}")
    private long expirationTime;

    @Value("${token.refresh_expiration}")
    private long refreshExpirationTime;


    public long getExpirationTime(boolean isRefreshToken) {
        if(isRefreshToken) return refreshExpirationTime;
        return expirationTime;
    }
}
