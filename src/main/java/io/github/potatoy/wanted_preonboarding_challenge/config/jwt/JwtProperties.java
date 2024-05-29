package io.github.potatoy.wanted_preonboarding_challenge.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/** application-{active profile}.yml 값들을 변수로 접근할 수 있도록 한다. */
@Setter
@Getter
@Component
@ConfigurationProperties("jwt") // Java Class에 프로티피값을 가져와 사용하는 애너테이션
public class JwtProperties {

  private String issuer;
  private String secretKey;
}
