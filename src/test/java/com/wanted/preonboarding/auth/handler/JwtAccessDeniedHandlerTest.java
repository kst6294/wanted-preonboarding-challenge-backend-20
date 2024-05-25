package com.wanted.preonboarding.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class JwtAccessDeniedHandlerTest {

    @InjectMocks
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Test
    @DisplayName("AccessDeniedHandler가 접근 권한이 없는 경우에 올바르게 응답을 반환하는지 테스트")
    void handleAccessDenied() throws IOException, ServletException {
        jwtAccessDeniedHandler.handle(request, response, new AccessDeniedException("Access is denied"));
        verify(response).sendError(HttpServletResponse.SC_FORBIDDEN, "접근 권한이 없습니다.");
    }



}