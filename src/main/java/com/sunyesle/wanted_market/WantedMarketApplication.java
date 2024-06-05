package com.sunyesle.wanted_market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class WantedMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(WantedMarketApplication.class, args);
	}

}
