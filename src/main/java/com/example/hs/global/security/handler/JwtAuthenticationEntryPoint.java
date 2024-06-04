package com.example.hs.global.security.handler;

import static com.example.hs.global.exception.ErrorCode.UN_AUTHORIZATION;

import com.example.hs.global.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
    ErrorResponse errorResponse;

    // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    errorResponse = ErrorResponse.builder()
        .httpCode(401)
        .errorMessage(UN_AUTHORIZATION.getDescription())
        .build();

    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    response.flushBuffer();

  }
}
