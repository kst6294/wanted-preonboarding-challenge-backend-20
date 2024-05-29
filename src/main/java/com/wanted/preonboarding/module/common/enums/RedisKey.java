package com.wanted.preonboarding.module.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.time.Duration;

@Getter
@AllArgsConstructor
public enum RedisKey implements EnumType {
    USERS("users", 36000),
    USERS_ENTITY("users_entity", 36000),
    PRODUCT_ENTITY("product_entity", 36000),
    REFRESH_TOKEN("refresh_token", 36000),
    ORDER_LOCK("order_lock", 1),
    PRODUCT_QUEUE("product_queue", 1),
    ;

    private final String key;
    private final int second;
    private static final String TERM = "::";

    public String generateKey(String key){
        if(StringUtils.hasText(key)) return this.key+TERM+key;
        return generateKey();
    }

    private String generateKey(){
        return this.key;
    }

    public Duration getSecondDuration(){
        return Duration.ofSeconds(this.second);
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getDescription() {
        return String.format("REDIS KEY :  %s, '\n'REDIS TTL :  %s", key, second);
    }
}
