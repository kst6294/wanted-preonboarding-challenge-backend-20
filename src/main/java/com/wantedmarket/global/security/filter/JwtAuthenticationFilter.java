package com.wantedmarket.global.security.filter;

import com.wantedmarket.global.security.exception.JwtException;
import com.wantedmarket.global.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

import static com.wantedmarket.global.security.exception.JwtErrorCode.EMPTY_TOKEN;
import static com.wantedmarket.global.security.exception.JwtErrorCode.INVALID_TOKEN;
import static com.wantedmarket.global.security.exception.JwtErrorCode.TOKEN_EXPIRED;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    static final String PREFIX = "Bearer ";
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        if (Objects.isNull(authHeader)) {
            throw new JwtException(EMPTY_TOKEN);
        }

        if (!authHeader.startsWith(PREFIX)) {
            throw new JwtException(INVALID_TOKEN);
        }

        jwt = authHeader.substring(PREFIX.length());

        if (jwtService.isTokenExpired(jwt)) {
            throw new JwtException(TOKEN_EXPIRED);
        }
        username = jwtService.extractUsername(jwt);
        if (Objects.nonNull(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);

        }
        filterChain.doFilter(request, response);
    }
}
