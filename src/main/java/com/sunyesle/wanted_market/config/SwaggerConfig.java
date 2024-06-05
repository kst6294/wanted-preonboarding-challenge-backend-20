package com.sunyesle.wanted_market.config;

import com.sunyesle.wanted_market.props.BearerTokenProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(BearerTokenProperties bearerTokenProperties) {
        final String securitySchemeName = "bearerAuth";

        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name(securitySchemeName);

        if (bearerTokenProperties.isEnabled() && !bearerTokenProperties.getTokens().isEmpty()) {
            List<BearerTokenProperties.BearerToken> tokens = bearerTokenProperties.getTokens();
            String description = tokens.stream()
                    .map(item -> String.format("**%s** %s", item.getName(), item.getToken()))
                    .collect(Collectors.joining("\n\n"));
            securityScheme.description(description);
        }

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, securityScheme)
                )
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .info(new Info()
                        .title("Wanted Market API")
                );
    }
}
