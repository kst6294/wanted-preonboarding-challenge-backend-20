package com.wanted.market_api.security.config;

import com.wanted.market_api.security.jwt.JwtAccessDeniedHandler;
import com.wanted.market_api.security.jwt.JwtAuthenticationEntryPoint;
import com.wanted.market_api.security.jwt.JwtFilter;
import com.wanted.market_api.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static com.wanted.market_api.constant.SecurityConstants.MEMBER_API_PATTERNS;
import static com.wanted.market_api.constant.SecurityConstants.PERMIT_ALL_API_PATTERNS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtProvider jwtProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(
                sessionManagement -> sessionManagement.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                )
        );

        http.authorizeHttpRequests(
                authorizeRequests -> authorizeRequests
                        .requestMatchers(PERMIT_ALL_API_PATTERNS).permitAll()
                        .requestMatchers(MEMBER_API_PATTERNS).hasRole("MEMBER")
                        .anyRequest().authenticated()
        );

        http.addFilterBefore(
                new JwtFilter(jwtProvider),
                UsernamePasswordAuthenticationFilter.class
        );

        http.exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
        );

        return http.build();
    }
}
