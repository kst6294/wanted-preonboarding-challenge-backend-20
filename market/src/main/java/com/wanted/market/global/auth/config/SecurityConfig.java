package com.wanted.market.global.auth.config;

import com.wanted.market.global.auth.common.CustomAuthenticationEntryPoint;
import com.wanted.market.global.auth.handler.CustomAccessDeniedHandler;
import com.wanted.market.global.auth.handler.CustomLogoutHandler;
import com.wanted.market.global.auth.handler.CustomLogoutSuccessHandler;
import com.wanted.market.domain.member.entity.RoleCode;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomLogoutHandler logoutHandler;
    private final CustomLogoutSuccessHandler logoutSuccessHandler;

    @Bean
    public SecurityFilterChain httpSecurityConfigure(HttpSecurity http) throws Exception {
        http
                .formLogin(form -> form.disable())
                .csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .headers(headers -> headers.disable());

        http
                .authorizeRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/api-docs/**").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/logout").permitAll()
                        .requestMatchers("/member/join").permitAll()
                        .requestMatchers("/product/register").hasAnyAuthority(String.valueOf(RoleCode.MEMBER))
                        .requestMatchers("/product/**").permitAll()
                        .requestMatchers("/trade/**").permitAll()
//                        .requestMatchers("/trade/**").hasAnyAuthority(String.valueOf(RoleCode.MEMBER))
                        .anyRequest().authenticated());

        http.logout(logout -> logout
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler(logoutSuccessHandler));

        http.exceptionHandling(handler -> handler
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint));

        return http.build();
    }
}
