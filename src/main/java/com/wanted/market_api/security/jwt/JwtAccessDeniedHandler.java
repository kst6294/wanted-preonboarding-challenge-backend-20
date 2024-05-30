package com.wanted.market_api.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.market_api.constant.ErrorCode;
import com.wanted.market_api.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(ErrorCode.INSUFFICIENT_AUTHORIZATION.getHttpStatus().value())
                .serverCode(ErrorCode.INSUFFICIENT_AUTHORIZATION.getCode())
                .message(ErrorCode.INSUFFICIENT_AUTHORIZATION.getMessage())
                .build();
        String json = objectMapper.writeValueAsString(errorResponse);

        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(json);
    }
}
