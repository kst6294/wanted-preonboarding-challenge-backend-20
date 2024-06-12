package com.example.hs.UnitTest.member;


import static com.example.hs.global.exception.ErrorCode.ALREADY_LOGOUT;
import static com.example.hs.global.exception.ErrorCode.INVALID_REFRESH_TOKEN;
import static com.example.hs.global.exception.ErrorCode.INVALID_TOKEN_REISSUE_REQUEST;
import static com.example.hs.global.exception.ErrorCode.NOT_MATCH_TOKEN_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.hs.global.exception.CustomException;
import com.example.hs.global.token.dto.TokenDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
public class MemberReIssueUnitTest extends MemberBaseUnitTest {


  @DisplayName("토큰 재발급 성공")
  @Test
  public void testReIssue_success() {
    String oldAccessToken = "oldAccessToken";
    String oldRefreshToken = "Bearer oldRefreshToken";
    String actualOldRefreshToken = "oldRefreshToken";
    String newAccessToken = "newAccessToken";
    String newRefreshToken = "newRefreshToken";

    Authentication authentication = mock(Authentication.class);
    when(authentication.getName()).thenReturn("testuser");
    when(tokenProvider.getAuthentication(actualOldRefreshToken)).thenReturn(authentication);
    when(tokenProvider.validateToken(actualOldRefreshToken)).thenReturn(true);
    when(tokenProvider.getUsername(actualOldRefreshToken)).thenReturn("testuser");
    when(tokenProvider.generateAccessToken(eq("testuser"), eq(authentication))).thenReturn(newAccessToken);
    when(tokenProvider.generateRefreshToken(eq("testuser"), eq(authentication))).thenReturn(newRefreshToken);

    TokenDto tokenDto = memberService.reIssue(oldAccessToken, oldRefreshToken);

    assertNotNull(tokenDto);
    assertEquals(newAccessToken, tokenDto.getAccessToken());
    assertEquals(newRefreshToken, tokenDto.getRefreshToken());
    verify(tokenRepository, times(1)).saveInValidAccessToken(eq("testuser"), eq(oldAccessToken));
    verify(tokenRepository, times(1)).saveRefreshToken(eq("testuser"), eq(newRefreshToken));
  }

  @DisplayName("토큰 재발급 실패: 유효하지 않은 리프레시 토큰")
  @Test
  public void testReIssue_fail_invalidRefreshToken() {
    String oldAccessToken = "Bearer oldAccessToken";
    String oldRefreshToken = "Bearer oldRefreshToken";
    String actualOldRefreshToken = "oldRefreshToken";

    when(tokenProvider.validateToken(actualOldRefreshToken)).thenReturn(false);

    CustomException exception = assertThrows(CustomException.class, () -> {
      memberService.reIssue(oldAccessToken, oldRefreshToken);
    });

    assertEquals(INVALID_REFRESH_TOKEN, exception.getErrorCode());
    verify(tokenRepository, never()).saveInValidAccessToken(anyString(), anyString());
    verify(tokenRepository, never()).saveRefreshToken(anyString(), anyString());
  }

  @DisplayName("토큰 재발급 실패: 사용자 불일치")
  @Test
  public void testReIssue_fail_userMismatch() {
    String oldAccessToken = "oldAccessToken";
    String oldRefreshToken = "Bearer oldRefreshToken";
    String actualOldRefreshToken = "oldRefreshToken";

    Authentication authentication = mock(Authentication.class);
    when(tokenProvider.getAuthentication(actualOldRefreshToken)).thenReturn(authentication);
    when(tokenProvider.validateToken(actualOldRefreshToken)).thenReturn(true);
    when(tokenProvider.getUsername(actualOldRefreshToken)).thenReturn("differentUser");

    CustomException exception = assertThrows(CustomException.class, () -> {
      memberService.reIssue(oldAccessToken, oldRefreshToken);
    });

    assertEquals(NOT_MATCH_TOKEN_USER, exception.getErrorCode());
    verify(tokenRepository, never()).saveInValidAccessToken(anyString(), anyString());
    verify(tokenRepository, never()).saveRefreshToken(anyString(), anyString());
  }

  @DisplayName("토큰 재발급 실패: 이미 로그아웃된 사용자")
  @Test
  public void testReIssue_fail_alreadyLoggedOut() {
    String oldAccessToken = "oldAccessToken";
    String oldRefreshToken = "Bearer oldRefreshToken";
    String actualOldRefreshToken = "oldRefreshToken";

    Authentication authentication = mock(Authentication.class);
    when(authentication.getName()).thenReturn("testuser");
    when(tokenProvider.getAuthentication(actualOldRefreshToken)).thenReturn(authentication);
    when(tokenProvider.validateToken(actualOldRefreshToken)).thenReturn(true);
    when(tokenProvider.getUsername(actualOldRefreshToken)).thenReturn("testuser");
    when(tokenRepository.getRefreshToken("testuser")).thenReturn(null);

    CustomException exception = assertThrows(CustomException.class, () -> {
      memberService.reIssue(oldAccessToken, oldRefreshToken);
    });

    assertEquals(ALREADY_LOGOUT, exception.getErrorCode());
    verify(tokenRepository, never()).saveInValidAccessToken("testuser", oldAccessToken);
    verify(tokenRepository, never()).saveRefreshToken("testuser", actualOldRefreshToken);
  }

  @DisplayName("토큰 재발급 실패: 리프레시 토큰 타입이 잘못됨")
  @Test
  public void testReIssue_fail_invalidTokenType() {
    String oldAccessToken = "Bearer oldAccessToken";
    String oldRefreshToken = "invalidRefreshTokenType";

    CustomException exception = assertThrows(CustomException.class, () -> {
      memberService.reIssue(oldAccessToken, oldRefreshToken);
    });

    assertEquals(INVALID_TOKEN_REISSUE_REQUEST, exception.getErrorCode());
    verify(tokenRepository, never()).saveInValidAccessToken(anyString(), anyString());
    verify(tokenRepository, never()).saveRefreshToken(anyString(), anyString());
  }
}
