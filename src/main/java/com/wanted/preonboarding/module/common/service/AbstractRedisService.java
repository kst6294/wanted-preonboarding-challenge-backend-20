package com.wanted.preonboarding.module.common.service;


import com.wanted.preonboarding.module.common.enums.RedisKey;
import com.wanted.preonboarding.module.exception.common.JsonSerializationException;
import com.wanted.preonboarding.module.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public abstract class AbstractRedisService implements RedisFetchService, RedisKeyGenerator, RedisQueryService{

    private final static String redisConnectionFailMessage = "Redis connection failure";
    private final static String errorAccessRedisMessage = "Error accessing Redis data";
    private final static String errorJsonMessage = "Failed to parse JSON: {}";
    private final StringRedisTemplate redisTemplate;

    private ValueOperations<String, String> opsValue() {
        return redisTemplate.opsForValue();
    }

    @Override
    public String generateKey(RedisKey redisKey, String key) {
        return redisKey.generateKey(key);
    }

    @Override
    public String getValue(String key) {
        try {
            return opsValue().get(key);
        } catch (RedisConnectionFailureException e) {
            throw new ServiceException(redisConnectionFailMessage, e);
        } catch (DataAccessException e) {
            throw new ServiceException(errorAccessRedisMessage, e);
        }
    }

    @Override
    public List<String> getValues(List<String> keys) {
        try {
            return opsValue().multiGet(keys);
        } catch (RedisConnectionFailureException e) {
            throw new ServiceException(redisConnectionFailMessage, e);
        } catch (DataAccessException e) {
            throw new ServiceException(errorAccessRedisMessage, e);
        }
    }

    @Override
    public void save(String key, String value){
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void save(String key, String value, Duration ttl){
        redisTemplate.opsForValue().set(key, value, ttl);
    }

    @Override
    public void delete(String key){
        redisTemplate.delete(key);
    }


    protected <T> T parseJson(String value, Class<T> clazz) {
        try {
            return JsonUtils.fromJson(value, clazz);
        } catch (JsonSerializationException e) {
            log.error("Failed to parse JSON: {}", e.getMessage());
            return null;
        }
    }


}
