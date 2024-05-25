package com.wanted.preonboarding.module.common.service;

import java.time.Duration;

public interface RedisQueryService {

    void save(String key, String value);
    void save(String key, String value, Duration ttl);
    void delete(String key);
}
