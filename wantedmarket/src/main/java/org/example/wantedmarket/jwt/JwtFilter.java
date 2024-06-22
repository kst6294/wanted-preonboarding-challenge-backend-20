package org.example.wantedmarket.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.wantedmarket.dto.user.CustomUserDetails;
import org.example.wantedmarket.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // request의 header에서 Authorization 추출
        String authorization = request.getHeader("Authorization");

        // Authorization 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.info("JwtFilter - Token is null");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.split(" ")[1];

        // token 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {
            log.info("JwtFilter - Token is expired");
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        // user 엔티티 생성
        User user = new User();
        user.setUsername(username);
        user.setPassword("temp");

        // UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        // 인증 토큰 생성
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        // 세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }

}
