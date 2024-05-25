package com.wanted.preonboarding.auth.filter;

import com.wanted.preonboarding.auth.AuthTokenProvider;
import com.wanted.preonboarding.auth.config.AuthConstants;
import com.wanted.preonboarding.module.exception.auth.TokenTypeException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthTokenProvider authTokenProvider;

    public JwtAuthenticationFilter(AuthTokenProvider authTokenProvider) {
        this.authTokenProvider = authTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwtToken = resolveToken(request);

        if (StringUtils.hasText(jwtToken)) {
            Authentication authentication = authTokenProvider.getAuthentication(jwtToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AuthConstants.AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken)) {
            if(bearerToken.startsWith(AuthConstants.BEARER_PREFIX)){
                return bearerToken.substring(AuthConstants.BEARER_PREFIX.length());
            }
            throw new TokenTypeException();
        }

        return null;
    }

}

