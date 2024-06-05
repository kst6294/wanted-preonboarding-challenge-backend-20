package com.example.hs.global.security;

import static com.example.hs.global.security.SecurityConstant.AUTH_WHITELIST;

import com.example.hs.global.security.filter.JwtAuthenticationFilter;
import com.example.hs.global.security.handler.JwtAccessDeniedHandler;
import com.example.hs.global.security.handler.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

  @Bean
  public PasswordEncoder PasswordEncoder () {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // 인가(접근권한) 설정
    http.authorizeRequests().requestMatchers(AUTH_WHITELIST).permitAll()
        .requestMatchers(HttpMethod.GET, "/goods", "/goods/**").permitAll()  // 모든 사용자가 상품 조회 가능
        .requestMatchers(HttpMethod.POST, "/goods").authenticated()  // 상품 등록은 로그인한 사용자만
        .requestMatchers(HttpMethod.PUT, "/goods/**").authenticated()  // 상품 수정은 로그인한 사용자만
        .requestMatchers(HttpMethod.DELETE, "/goods/**").authenticated();  // 상품 삭제는 로그인한 사용자만

    http.csrf(csrf -> csrf.disable())
        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    http
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling((exceptionConfig) ->
            exceptionConfig.authenticationEntryPoint(jwtAuthenticationEntryPoint).accessDeniedHandler(jwtAccessDeniedHandler)
        );
    return http.build();
  }
}
