package com.example.hs.global.token.repository;



import static com.example.hs.global.redis.constant.RedisKeyPrefix.INVALID_ACCESS_TOKEN_HEADER;
import static com.example.hs.global.redis.constant.RedisKeyPrefix.REFRESH_TOKEN_HEADER;
import static com.example.hs.global.token.constant.TokenConstant.ACCESS_TOKEN_EXPIRE_TIME;
import static com.example.hs.global.token.constant.TokenConstant.REFRESH_TOKEN_EXPIRE_TIME;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TokenRepository {
  private final RedisTemplate<String, String> redisTemplate;

  public void saveRefreshToken(String userId, String refreshToken) {

    if (redisTemplate != null) {
      String key = REFRESH_TOKEN_HEADER + userId;
      redisTemplate.opsForValue().set(key, refreshToken, Duration.ofSeconds(
          REFRESH_TOKEN_EXPIRE_TIME));
    } else {
      log.error("StringRedisTemplate is null. Check configuration.");
      throw new IllegalStateException("StringRedisTemplate is null. Check configuration.");
    }
  }

  public void saveInValidAccessToken(String userId, String invalidAccessToken) {
    if (redisTemplate != null) {
      String key = INVALID_ACCESS_TOKEN_HEADER + userId;
      redisTemplate.opsForValue().set(key, invalidAccessToken, Duration.ofSeconds(
          ACCESS_TOKEN_EXPIRE_TIME));
    } else {
      log.error("StringRedisTemplate is null. Check configuration.");
      throw new IllegalStateException("StringRedisTemplate is null. Check configuration.");
    }
  }

  public String getRefreshToken(String userId) {
    String key = REFRESH_TOKEN_HEADER + userId;
    return redisTemplate.opsForValue().get(key);
  }

  public void deleteRefreshToken(String userId) {
    String key = REFRESH_TOKEN_HEADER + userId;
    redisTemplate.delete(key);
  }

  public String getInvalidAccessToken(String userId) {
    String key = INVALID_ACCESS_TOKEN_HEADER + userId;
    return redisTemplate.opsForValue().get(key);
  }
}
