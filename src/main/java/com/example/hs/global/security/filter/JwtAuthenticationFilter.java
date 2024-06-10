package com.example.hs.global.security.filter;

import static com.example.hs.global.exception.ErrorCode.INVALID_ACCESS_TOKEN;
import static com.example.hs.global.token.constant.TokenConstant.AUTHORIZATION_HEADER;
import static com.example.hs.global.token.constant.TokenConstant.BEARER_TYPE;

import com.example.hs.global.exception.CustomException;
import com.example.hs.global.token.provider.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final TokenProvider tokenProvider;


  // 실제 필터링 로직은 doFilterInternal 에 들어감
  // JWT 토큰의 인증 정보를 현재 쓰레드의 SecurityContext 에 저장하는 역할 수행
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

    // 1. Request Header 에서 토큰을 꺼냄
    String jwt = resolveToken(request);
    // 2. validateToken 으로 토큰 유효성 검사
    // 정상 토큰이면 해당 토큰으로 Authentication 을 가져와서 SecurityContext 에 저장
    Authentication authentication = null;
    if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
      authentication = tokenProvider.getAuthentication(jwt);
      log.info("Principal class: {}", authentication.getPrincipal().getClass().getName());

      SecurityContextHolder.getContext().setAuthentication(authentication);
      log.info("Authentication successful for user: {}", authentication.getName());
    }

    if (authentication != null && tokenProvider.checkInValidAccessToken(authentication.getName(), jwt)) {
      throw new CustomException(INVALID_ACCESS_TOKEN);
    }
    filterChain.doFilter(request, response);
  }

  // Request Header 에서 토큰 정보를 꺼내오기
  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE)) {
      return bearerToken.substring(BEARER_TYPE.length());
    }
    return null;
  }
}
