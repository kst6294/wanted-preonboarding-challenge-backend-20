package com.wanted.preonboarding.auth.filter;

import com.wanted.preonboarding.auth.AuthTokenProvider;
import com.wanted.preonboarding.auth.config.AuthConstants;
import com.wanted.preonboarding.auth.core.AuthToken;
import com.wanted.preonboarding.module.exception.auth.TokenTypeException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private AuthTokenProvider authTokenProvider;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private Authentication authentication;

    @Mock
    private AuthToken authToken;

    @BeforeEach
    void setUp() {
        jwtAuthenticationFilter = new JwtAuthenticationFilter(authTokenProvider);
    }

    @Test
    @DisplayName("토큰이 유효한 경우 필터가 인증 정보를 설정하는지 테스트")
    void testDoFilterInternal_ValidToken() throws ServletException, IOException {
        String token = "validToken";
        when(request.getHeader(AuthConstants.AUTHORIZATION_HEADER)).thenReturn(AuthConstants.BEARER_PREFIX + token);
        when(authTokenProvider.getAuthentication(token)).thenReturn(authentication);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(authTokenProvider, times(1)).getAuthentication(token);
        verify(filterChain, times(1)).doFilter(request, response);
        assertEquals(authentication, SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("토큰이 없는 경우 필터가 인증 정보를 설정하지 않는지 테스트")
    void testDoFilterInternal_NoToken() throws ServletException, IOException {
        when(request.getHeader(AuthConstants.AUTHORIZATION_HEADER)).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(authTokenProvider, never()).getAuthentication(anyString());
        verify(filterChain, times(1)).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("유효하지 않은 토큰이 주어졌을 때 예외를 던지는지 테스트")
    void testDoFilterInternal_InvalidToken()  {
        String invalidToken = "invalidToken";
        when(request.getHeader(AuthConstants.AUTHORIZATION_HEADER)).thenReturn( invalidToken);

        assertThrows(TokenTypeException.class, () -> {
            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        });

    }
}