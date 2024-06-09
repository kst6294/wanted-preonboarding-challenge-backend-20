package com.exception_study.global.config.filter;

import io.jsonwebtoken.JwtException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {
    /**
     * switch문 case를 실제 예외 클래스를 비교하는 것으로 변경할 것 고려
     * 현재는 .getClass활용 시 좀 복잡한 코드로 작성될걸 우려해서 고민해서 적용해봐야할듯 함
     */

    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            String message = e.getMessage();
            switch (message) {
                case "SignatureException" -> setResponse(response, "잘못된 타입의 토큰입니다.");
                case "MalformedJwtException" -> setResponse(response, "지원하지 않는 토큰입니다.");
                case "ExpiredJwtException" -> setResponse(response, "만료된 토큰입니다.");
                case "IllegalArgumentException" -> setResponse(response, "잘못된 요청입니다.");
            }
        }
    }


    private void setResponse(HttpServletResponse response, String errorMessage) throws RuntimeException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(401);
        response.getWriter().print(errorMessage);
    }
}