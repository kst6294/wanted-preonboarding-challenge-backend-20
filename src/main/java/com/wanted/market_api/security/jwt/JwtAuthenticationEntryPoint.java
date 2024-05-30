package com.wanted.market_api.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.market_api.constant.ErrorCode;
import com.wanted.market_api.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(ErrorCode.UNAUTHORIZED_JWT.getHttpStatus().value())
                .serverCode(ErrorCode.UNAUTHORIZED_JWT.getCode())
                .message(ErrorCode.UNAUTHORIZED_JWT.getMessage())
                .build();
        String json = objectMapper.writeValueAsString(errorResponse);

        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(json);
    }
}
