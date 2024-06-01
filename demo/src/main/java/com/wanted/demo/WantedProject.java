package com.wanted.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WantedProject {

	public static void main(String[] args) {
		SpringApplication.run(WantedProject.class, args);
	}

}
