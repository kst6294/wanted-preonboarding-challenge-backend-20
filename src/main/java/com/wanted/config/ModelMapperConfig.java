package com.wanted.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper getMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
		           .setFieldMatchingEnabled(true)
		           .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
		           .setMatchingStrategy(MatchingStrategies.LOOSE);

		return modelMapper;
	}

}
