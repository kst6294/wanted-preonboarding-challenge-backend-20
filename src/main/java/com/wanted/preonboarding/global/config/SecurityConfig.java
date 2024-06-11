package com.wanted.preonboarding.global.config;

import com.wanted.preonboarding.global.filter.CustomerSessionFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomerSessionFilter sessionFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       http
               .csrf(CsrfConfigurer::disable)
               .httpBasic(HttpBasicConfigurer::disable)
               .authorizeHttpRequests(requests -> requests
                       .requestMatchers("/api/v1/user/login", "/api/v1/user/join", "/api/v1/user/email").permitAll()
                       .requestMatchers(HttpMethod.GET, "/api/v1/product/purchase", "/api/v1/product/reservation").authenticated()
                       .requestMatchers(HttpMethod.GET, "/api/v1/product/**").permitAll()
                       .requestMatchers("/api/v1/product/**").authenticated()
                       .requestMatchers(HttpMethod.POST, "/api/v1/purchase", "/api/v1/purchase/accept", "/api/v1/purchase/confirm").authenticated()
                       .anyRequest().denyAll()
               )
               .addFilterBefore(sessionFilter, UsernamePasswordAuthenticationFilter.class)
               .formLogin(FormLoginConfigurer::disable)
               .logout(LogoutConfigurer::disable)
       ;
        return http.build();
    }

}
