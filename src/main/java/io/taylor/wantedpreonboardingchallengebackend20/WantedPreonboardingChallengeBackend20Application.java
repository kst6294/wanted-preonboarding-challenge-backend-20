package io.taylor.wantedpreonboardingchallengebackend20;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class WantedPreonboardingChallengeBackend20Application {

    public static void main(String[] args) {
        SpringApplication.run(WantedPreonboardingChallengeBackend20Application.class, args);
    }

}
