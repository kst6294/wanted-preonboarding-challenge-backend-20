package com.example.wantedmarketapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.wantedmarketapi.security.filter.JwtAuthExceptionHandlingFilter;
import com.example.wantedmarketapi.security.filter.JwtRequestFilter;
import com.example.wantedmarketapi.security.handler.JwtAccessDeniedHandler;
import com.example.wantedmarketapi.security.handler.JwtAuthenticationEntryPoint;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtRequestFilter jwtRequestFilter;
    private final JwtAuthExceptionHandlingFilter jwtAuthExceptionHandlingFilter;

    private final String[] allowedUrls = {"/api/v1/members/signup"};

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web ->
                web.ignoring()
                        .requestMatchers(
                                "/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**", "/health", "/h2-console/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);

        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        http.sessionManagement(
                sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.exceptionHandling(
                (configurer ->
                        configurer
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler)));

        http.authorizeHttpRequests(
                (authorize) ->
                        authorize.requestMatchers(allowedUrls).permitAll().anyRequest().authenticated());

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthExceptionHandlingFilter, JwtRequestFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}