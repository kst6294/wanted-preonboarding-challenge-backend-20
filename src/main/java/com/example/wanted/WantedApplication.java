package com.example.wanted;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WantedApplication {

    public static void main(String[] args) {
        SpringApplication.run(WantedApplication.class, args);
    }

}
