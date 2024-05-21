package wanted.market.api.common.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import wanted.market.api.common.jwt.JwtFilter
import wanted.market.api.common.jwt.JwtTokenProvider

@EnableWebSecurity
@Configuration
class SecurityConfig(
    private val authenticationEntryPoint: CustomAuthenticationEntryPoint,
    private val accessDeniedHandler: CustomAccessDeniedHandler,
    private val jwtTokenProvider: JwtTokenProvider
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .httpBasic { it.disable() }
            .cors { it.disable() }
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .exceptionHandling { exceptionHandling ->
                exceptionHandling.authenticationEntryPoint(authenticationEntryPoint)
                exceptionHandling.accessDeniedHandler(accessDeniedHandler)
            }
            .authorizeHttpRequests { authorize ->
                authorize.requestMatchers(
                    "/auth/**",
                    "/favicon.ico",
                    "/file/**",
                    "/v3/**",
                    "/swagger-ui/**",
                    "/fcm",
                    "/firebase-messaging-sw.js",
                    "/*.js",
                    "/actuator/**"
                ).permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(
                JwtFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
