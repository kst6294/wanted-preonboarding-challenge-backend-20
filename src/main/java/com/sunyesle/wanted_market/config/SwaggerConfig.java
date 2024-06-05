package com.sunyesle.wanted_market.config;

import com.sunyesle.wanted_market.props.BearerTokenProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(BearerTokenProperties bearerTokenProperties) {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .name(securitySchemeName))
                )
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .info(new Info()
                        .title("Wanted Market API")
                );
    }
}