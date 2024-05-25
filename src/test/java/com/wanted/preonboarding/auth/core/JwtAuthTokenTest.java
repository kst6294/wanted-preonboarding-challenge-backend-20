package com.wanted.preonboarding.auth.core;

import com.wanted.preonboarding.data.auth.AuthModuleHelper;
import com.wanted.preonboarding.infra.config.jwt.JwtConfig;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Key;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtAuthTokenTest {


    @Test
    @DisplayName("토큰 생성 테스트")
    void createAuthToken() {
        JwtAuthToken jwtAuthToken = AuthModuleHelper.toJwtAuthToken_another_constructor();
        assertNotNull(jwtAuthToken);
        assertNotNull(jwtAuthToken.getToken());
        assertNotNull(jwtAuthToken.getSubject());
    }

    @Test
    @DisplayName("만료된 토큰 파싱 테스트")
    void parseClaimsJws_ExpiredToken() {
        JwtAuthToken expiredToken = AuthModuleHelper.toExpireJwtAuthToken_another_constructor();
        assertThrows(ExpiredJwtException.class, expiredToken::getSubject);
    }

    @Test
    @DisplayName("토큰 파싱 테스트 - 잘못된 서명 토큰")
    void parseClaimsJws_InvalidSignatureToken() {
        JwtAuthToken jwtAuthToken = AuthModuleHelper.toJwtAuthToken_another_constructor();
        Key anotherKey = mock(Key.class);
        JwtAuthToken invalidToken = new JwtAuthToken(anotherKey, jwtAuthToken.getToken());
        assertThrows(JwtException.class, invalidToken::getSubject);
    }

}