package com.wanted.preonboarding.module.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.time.Duration;

@Getter
@AllArgsConstructor
public enum RedisKey implements EnumType {
    USERS("users", 1),
    REFRESH_TOKEN("refresh_token", 1),;


    private final String key;
    private final int hour;
    private static final String TERM = "::";

    public String generateKey(String key){
        if(StringUtils.hasText(key)) return this.key+TERM+key;
        return generateKey();
    }

    private String generateKey(){
        return this.key;
    }

    public Duration getHourDuration(){
        return Duration.ofHours(this.hour);
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getDescription() {
        return String.format("REDIS KEY %s, REDIS TTL %s", key, hour);
    }
}
