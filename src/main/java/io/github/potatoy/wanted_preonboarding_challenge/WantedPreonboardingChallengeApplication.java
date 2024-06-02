package io.github.potatoy.wanted_preonboarding_challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WantedPreonboardingChallengeApplication {

  public static void main(String[] args) {
    SpringApplication.run(WantedPreonboardingChallengeApplication.class, args);
  }
}
