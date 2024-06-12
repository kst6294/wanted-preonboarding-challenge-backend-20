package com.example.hs.UnitTest.member;


import static com.example.hs.global.exception.ErrorCode.NOT_AUTHENTICATE_EMAIL;
import static com.example.hs.global.exception.ErrorCode.NOT_MATCH_PASSWORD;
import static com.example.hs.global.token.constant.TokenConstant.ACCESS_TOKEN_EXPIRE_TIME;
import static com.example.hs.global.token.constant.TokenConstant.REFRESH_TOKEN_EXPIRE_TIME;
import static com.example.hs.global.token.constant.TokenConstant.key;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.hs.global.exception.CustomException;
import com.example.hs.global.token.dto.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.Collections;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@ExtendWith(MockitoExtension.class)
public class MemberSignInUnitTest extends MemberBaseUnitTest{


  @DisplayName("로그인 성공")
  @Test
  public void testSignIn_success() {
    String loginId = "testuser";
    String password = "password";

    Authentication authentication = mock(Authentication.class);
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
    doReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_MEMBER"))).when(authentication).getAuthorities();
    doReturn(loginId).when(authentication).getName();

    when(tokenProvider.generateAccessToken(eq(loginId), eq(authentication)))
        .thenReturn(Jwts.builder()
            .setSubject(loginId)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME))
            .signWith(key)
            .compact());

    when(tokenProvider.generateRefreshToken(eq(loginId), eq(authentication)))
        .thenReturn(Jwts.builder()
            .setSubject(loginId)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_TIME))
            .signWith(key)
            .compact());

    TokenDto tokenDto = memberService.signIn(loginId, password);

    //then > 로그인 성공, tokenDto 반환
    assertNotNull(tokenDto);
    assertNotNull(tokenDto.getAccessToken());
    assertNotNull(tokenDto.getRefreshToken());
    verify(tokenRepository, times(1)).saveRefreshToken(eq(loginId), anyString());

    // when
    when(tokenProvider.parseClaims(eq(tokenDto.getAccessToken()))).thenReturn(Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(tokenDto.getAccessToken()).getBody());
    when(tokenProvider.parseClaims(eq(tokenDto.getRefreshToken()))).thenReturn(Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(tokenDto.getRefreshToken()).getBody());

    // then > 토큰 만료 시간 검증
    Claims accessClaims = tokenProvider.parseClaims(tokenDto.getAccessToken());
    Date accessExpirationDate = accessClaims.getExpiration();
    assertNotNull(accessExpirationDate);
    assertTrue(accessExpirationDate.after(new Date()));
    assertTrue(accessExpirationDate.getTime() - accessClaims.getIssuedAt().getTime() <= ACCESS_TOKEN_EXPIRE_TIME);

    Claims refreshClaims = tokenProvider.parseClaims(tokenDto.getRefreshToken());
    Date refreshExpirationDate = refreshClaims.getExpiration();
    assertNotNull(refreshExpirationDate);
    assertTrue(refreshExpirationDate.after(new Date()));
    assertTrue(refreshExpirationDate.getTime() - refreshClaims.getIssuedAt().getTime() <= REFRESH_TOKEN_EXPIRE_TIME);
  }

  @DisplayName("로그인 실패2: 잘못된 비밀번호")
  @Test
  public void testSignIn_fail_wrongPassword() {
    String loginId = "testuser";
    String password = "wrongPassword";

    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenThrow(new CustomException(NOT_MATCH_PASSWORD));

    CustomException exception = assertThrows(CustomException.class, () -> {
      memberService.signIn(loginId, password);
    });

    assertEquals(NOT_MATCH_PASSWORD, exception.getErrorCode());
  }

  @DisplayName("로그인 실패2: 이메일 인증 안된 사용자")
  @Test
  public void testSignIn_fail_notAuthenticated() {
    String loginId = "testuser";
    String password = "password";

    Authentication authentication = mock(Authentication.class);
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
    doReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_NOT_YET_MEMBER"))).when(authentication).getAuthorities();

    CustomException exception = assertThrows(CustomException.class, () -> {
      memberService.signIn(loginId, password);
    });

    assertEquals(NOT_AUTHENTICATE_EMAIL, exception.getErrorCode());
  }
}
