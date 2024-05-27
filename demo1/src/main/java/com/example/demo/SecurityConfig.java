package com.example.demo;

import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v1/member/save").permitAll()
                        .requestMatchers("/api/v1/item/save").hasRole("USER")
                        .requestMatchers("/api/v1/item/buy").hasRole("USER")
                        .requestMatchers("/api/v1/item/{id}").permitAll() //상세조회 및 목록조회
                        .requestMatchers("/api/v1/item").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formlogin -> formlogin
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable());


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
