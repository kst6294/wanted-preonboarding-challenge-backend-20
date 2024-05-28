package com.wanted.preonboarding.backend20;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Backend20Application {

	public static void main(String[] args) {
		SpringApplication.run(Backend20Application.class, args);
	}

}
