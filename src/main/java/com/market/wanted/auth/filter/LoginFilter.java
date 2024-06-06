package com.market.wanted.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.wanted.auth.dto.LoginRequest;
import com.market.wanted.auth.entity.RefreshEntity;
import com.market.wanted.auth.repository.RefreshRepository;
import com.market.wanted.auth.security.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@Slf4j
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final RefreshRepository refreshRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//         username password 추출
        ObjectMapper mapper = new ObjectMapper();
        LoginRequest loginRequest = null;
        try {
            loginRequest = mapper.readValue(request.getInputStream(), LoginRequest.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();



        log.info("username = {}, password = {}", username, password);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        String username = authResult.getName();
        String role = getRole(authResult);

        //토큰 생성
        String access = jwtUtil.createJwt("access", username,  60000L);
        long refreshExpired = 86400000L;
        String refresh = jwtUtil.createJwt("refresh", username, refreshExpired);

        addRefreshEntity(username, refresh, refreshExpired);

        response.setHeader("Authorization", "Bearer " + access);
        response.addCookie(createCookie("refreshToken", refresh));
        response.setStatus(HttpStatus.OK.value());

    }

    private void addRefreshEntity(String username, String refreshToken, Long expired) {
        Date date = new Date(System.currentTimeMillis() + expired);
        RefreshEntity refreshEntity =
                RefreshEntity.builder()
                        .refreshToken(refreshToken)
                        .expiration(date.toString())
                        .username(username)
                        .build();
        refreshRepository.save(refreshEntity);
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setHttpOnly(true);
        return cookie;
    }



    private String getRole(Authentication authResult) {
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        return auth.getAuthority();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(401);
    }
}
