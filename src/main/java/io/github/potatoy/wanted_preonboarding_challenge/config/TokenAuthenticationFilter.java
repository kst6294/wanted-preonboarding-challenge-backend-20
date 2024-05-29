package io.github.potatoy.wanted_preonboarding_challenge.config;

import io.github.potatoy.wanted_preonboarding_challenge.config.jwt.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

  private static final String HEADER_AUTHORIZATION = "Authorization";
  private static final String TOKEN_PREFIX = "Bearer ";
  private final TokenProvider tokenProvider;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    // 요청 헤더의 Authentication 키의 값 조회
    String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
    // 가져온 값에서 접두사 제거
    String token = getAccessToken(authorizationHeader);

    // 가져온 토큰이 유효한지 확인하고, 유효한 때는 인증 정보를 설정
    if (tokenProvider.validToken(token)) {
      Authentication authentication = tokenProvider.getAuthentication(token);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);
  }

  /**
   * 정상적인 Token인 경우 접두사를 제거
   *
   * @param authorizationHeader Header
   * @return 접두사가 제거된 Token 내용
   */
  private String getAccessToken(String authorizationHeader) {
    if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
      return authorizationHeader.substring(TOKEN_PREFIX.length());
    }

    return null;
  }
}
