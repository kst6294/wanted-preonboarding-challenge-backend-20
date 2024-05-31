package io.github.potatoy.wanted_preonboarding_challenge.config;

import io.github.potatoy.wanted_preonboarding_challenge.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final TokenProvider tokenProvider;

  /**
   * Spring Security 기능 비활성화 - DB 페이지에 대하여 비활성화 적용
   *
   * @return WebSecurityCustomizer
   */
  @Bean
  public WebSecurityCustomizer configure() {
    return web -> web.ignoring().requestMatchers("/h2/**");
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.csrf(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .logout(AbstractHttpConfigurer::disable)

        // JWT 사용을 위해 세션 사용 비활성화
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        // 헤더를 확인하는 커스텀 필터 추가
        .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .authorizeHttpRequests(
            authz ->
                authz
                    // 로그인, 회원가입, 토큰 갱신을 제외한 api는 인증을 하도록 설정
                    .requestMatchers(new AntPathRequestMatcher("/api/auth/**"))
                    .permitAll()
                    .requestMatchers("/api/**")
                    .authenticated()
                    .anyRequest()
                    .permitAll())
        .build();
  }

  /**
   * Password 인코더로 사용할 빈 등록
   *
   * @return BCryptPasswordEncoder
   */
  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Token 인증 필터 빈 등록
   *
   * @return TokenAuthenticationFilter
   */
  @Bean
  public TokenAuthenticationFilter tokenAuthenticationFilter() {
    return new TokenAuthenticationFilter(tokenProvider);
  }
}
