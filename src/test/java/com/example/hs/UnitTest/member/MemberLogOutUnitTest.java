package com.example.hs.UnitTest.member;


import static com.example.hs.global.exception.ErrorCode.ALREADY_LOGOUT;
import static com.example.hs.global.exception.ErrorCode.INVALID_ACCESS_TOKEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.hs.domain.auth.dto.LogoutResponse;
import com.example.hs.global.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
public class MemberLogOutUnitTest extends MemberBaseUnitTest{

  @DisplayName("로그아웃 성공")
  @Test
  public void testLogout_success() {
    String loginId = "testuser";
    String accessToken = "Bearer validAccessToken";
    String actualAccessToken = "validAccessToken";
    String refreshToken = "validRefreshToken";

    Authentication authentication = mock(Authentication.class);
    when(authentication.getName()).thenReturn(loginId);
    when(tokenProvider.getAuthentication(actualAccessToken)).thenReturn(authentication);
    when(tokenProvider.validateToken(actualAccessToken)).thenReturn(true);
    when(tokenRepository.getRefreshToken(loginId)).thenReturn(refreshToken);

    LogoutResponse response = memberService.logout(accessToken);

    assertNotNull(response);
    assertEquals(loginId, response.getLoginId());
    assertEquals("로그아웃 되었습니다.", response.getMessage());
    verify(tokenRepository, times(1)).saveInValidAccessToken(eq(loginId), eq(actualAccessToken));
    verify(tokenRepository, times(1)).deleteRefreshToken(eq(loginId));
  }

  @DisplayName("로그아웃 실패1: 유효하지 않은 토큰")
  @Test
  public void testLogout_fail_invalidToken() {
    String accessToken = "Bearer invalidAccessToken";
    String actualAccessToken = "invalidAccessToken";

    when(tokenProvider.validateToken(actualAccessToken)).thenReturn(false);

    CustomException exception = assertThrows(CustomException.class, () -> {
      memberService.logout(accessToken);
    });

    assertEquals(INVALID_ACCESS_TOKEN, exception.getErrorCode());
    verify(tokenRepository, never()).saveInValidAccessToken(anyString(), anyString());
    verify(tokenRepository, never()).deleteRefreshToken(anyString());
  }

  @DisplayName("로그아웃 실패2: 이미 로그아웃된 사용자")
  @Test
  public void testLogout_fail_alreadyLoggedOut() {
    String loginId = "testuser";
    String accessToken = "Bearer validAccessToken";
    String actualAccessToken = "validAccessToken";

    Authentication authentication = mock(Authentication.class);
    when(authentication.getName()).thenReturn(loginId);
    when(tokenProvider.getAuthentication(actualAccessToken)).thenReturn(authentication);
    when(tokenProvider.validateToken(actualAccessToken)).thenReturn(true);
    when(tokenRepository.getRefreshToken(loginId)).thenReturn(null);

    CustomException exception = assertThrows(CustomException.class, () -> {
      memberService.logout(accessToken);
    });

    assertEquals(ALREADY_LOGOUT, exception.getErrorCode());
    verify(tokenRepository, never()).saveInValidAccessToken(anyString(), anyString());
    verify(tokenRepository, never()).deleteRefreshToken(anyString());
  }
}
