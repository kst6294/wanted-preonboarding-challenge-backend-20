package com.example.hs.global.security.handler;


import static com.example.hs.global.exception.ErrorCode.NOT_MATCH_AUTHORIZATION;

import com.example.hs.global.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
    ErrorResponse errorResponse;

    // 필요한 권한이 없이 접근하려 할때 403
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);

    errorResponse = ErrorResponse.builder()
        .httpCode(403)
        .errorMessage(NOT_MATCH_AUTHORIZATION.getDescription())
        .build();

    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    response.flushBuffer();
  }
}
