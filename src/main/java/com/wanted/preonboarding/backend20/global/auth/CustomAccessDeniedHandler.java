package com.wanted.preonboarding.backend20.global.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.preonboarding.backend20.global.exception.ErrorCode;
import com.wanted.preonboarding.backend20.global.exception.ErrorResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("[AccessDeniedHandler] - 해당 엔드포인트에 접근할 권한 없음: {}, {}", request.getUserPrincipal().getName(), request.getServletPath());
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorResponseDto errorResponseDto = ErrorResponseDto.toResponseEntity(ErrorCode.PERMISSION_DENIED, "해당 작업을 수행할 권한이 없습니다.").getBody();

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponseDto));
    }
}
