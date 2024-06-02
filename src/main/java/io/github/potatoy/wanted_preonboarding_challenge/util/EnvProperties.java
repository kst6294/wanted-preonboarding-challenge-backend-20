package io.github.potatoy.wanted_preonboarding_challenge.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("env")
public class EnvProperties {

  private String mode;
}
