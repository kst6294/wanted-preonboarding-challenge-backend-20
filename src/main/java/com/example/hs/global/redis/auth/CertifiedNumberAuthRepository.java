package com.example.hs.global.redis.auth;

import static com.example.hs.global.mail.MailConstant.EMAIL_VERIFICATION_LIMIT_IN_SECONDS;
import static com.example.hs.global.redis.constant.RedisKeyPrefix.EMAIL_AUTH;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CertifiedNumberAuthRepository {
  private final RedisTemplate<String, String> redisTemplate;

  public void saveCertificationNumber(String email, String certificationNumber) {
    if (redisTemplate != null) {
      redisTemplate.opsForValue()
          .set(EMAIL_AUTH + email, certificationNumber, Duration.ofSeconds(EMAIL_VERIFICATION_LIMIT_IN_SECONDS));
    } else {
      log.error("StringRedisTemplate is null. Check configuration.");
      throw new IllegalStateException("StringRedisTemplate is null. Check configuration.");
    }
  }

  public String getCertificationNumber(String email) {
    return redisTemplate.opsForValue().get(EMAIL_AUTH + email);
  }

  public void removeCertificationNumber(String email) {
    redisTemplate.delete(EMAIL_AUTH + email);
  }

  public boolean hasKeyForEmailAuth(String email) {
    Boolean keyExists = redisTemplate.hasKey(EMAIL_AUTH + email);
    return keyExists != null && keyExists;
  }
}
