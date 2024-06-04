package com.wanted.market.global.auth.provider;

import com.wanted.market.global.auth.common.CustomUserDetails;
import com.wanted.market.global.auth.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("CustomAuthenticationProvider.authenticate");

        String inputPassword = authentication.getCredentials().toString();
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(authentication.getName());

        if(!passwordEncoder.matches(inputPassword, userDetails.getPassword())) {
            log.info("User not found with username : {}", userDetails.getUsername());
            throw new BadCredentialsException("Bad credentials");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
