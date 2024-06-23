package kr.co.wanted.market.common.global.properties;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Slf4j
@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "jwt")
public class JWTProperties {

    private final String secret;

    private final long expirationTime;

    @PostConstruct
    public void log() {
        log.debug("JWT secret: [{}], expirationTime: [{}]", secret, expirationTime);
    }

}
