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
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class JwtAuthorizationDeniedHandlerTest {

    @InjectMocks
    private JwtAuthorizationDeniedHandler jwtAuthorizationDeniedHandler;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private AuthenticationException authException;

    @Test
    @DisplayName("AuthenticationEntryPoint가 로그인 필요 메시지를 올바르게 반환하는지 테스트")
    void handleAccessDenied() throws IOException, ServletException {
        jwtAuthorizationDeniedHandler.commence(request, response, authException);
        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
    }
}