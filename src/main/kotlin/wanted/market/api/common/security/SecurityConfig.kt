package wanted.market.api.common.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
@Configuration
class SecurityConfig {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .sessionManagement { session -> session.sessionCreationPolicy(STATELESS) }
            .authorizeHttpRequests { authorize ->
                authorize.requestMatchers("/auth/v1/**", "/favicon.ico").permitAll()
                         .anyRequest().authenticated()
            }
            .build()
    }
}
