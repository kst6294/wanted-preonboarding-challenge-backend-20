package com.wanted.preonboarding.backend20.global.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.preonboarding.backend20.global.exception.ErrorCode;
import com.wanted.preonboarding.backend20.global.exception.ErrorResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        log.info("[AuthenticationEntryPoint] - 인증 실패: {}, endPoint: {}", authException.getMessage(), request.getServletPath());
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorResponseDto errorResponseDto = ErrorResponseDto.toResponseEntity(ErrorCode.AUTHENTICATION_ERROR, "인증 실패").getBody();

        response.setStatus(response.getStatus());
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponseDto));
    }
}