package com.sunyesle.wanted_market.global.props;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "springdoc.bearer")
public class BearerTokenProperties {
    private final boolean enabled;
    private final List<BearerToken> tokens;

    @Getter
    @RequiredArgsConstructor
    public static class BearerToken {
        private final String name;
        private final String token;
    }
}