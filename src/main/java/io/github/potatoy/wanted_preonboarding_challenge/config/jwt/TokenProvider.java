package io.github.potatoy.wanted_preonboarding_challenge.config.jwt;

import io.github.potatoy.wanted_preonboarding_challenge.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

/**
 * 토큰 생성, 유효성 검사, 토큰에서 정보 추출을 담당하는 클래스
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class TokenProvider {

  private final JwtProperties jwtProperties;

  /**
   * JWT 토큰 생성
   *
   * @param user      user 객체
   * @param expiredAt 유효 시간
   * @return Token
   */
  public String generateToken(User user, Duration expiredAt) {
    Date now = new Date();
    return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
  }

  /**
   * JWT 토큰을 만들어 반환
   *
   * @param expiry 유효 기간
   * @param user   user 객체
   * @return Token
   */
  private String makeToken(Date expiry, User user) {
    Date now = new Date();

    log.info("makeToken. token 생성. userId={}", user.getId());
    return Jwts.builder()
        .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // Header Type: JWT
        // 내용 iss: properties에서 가져온 값
        .setIssuer(jwtProperties.getIssuer())
        .setIssuedAt(now) // 내용 isa: 현재 시간
        .setExpiration(expiry) // 내용 exp: expiry 멤버 변수값
        .setSubject(user.getEmail()) // 내용 sub: User email
        .claim("id", user.getId())
        .claim("nickname", user.getNickname())
        // 서명: 비밀값과 함께 해시값을 HS256 방식으로 암호화
        .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
        .compact();
  }

  /**
   * 유효한 토큰인지 확인
   *
   * @param token Token
   * @return boolean true: 검증 성공, false: 검증 실패
   */
  public boolean validToken(String token) {
    try {
      Jwts.parser()
          .setSigningKey(jwtProperties.getSecretKey()) // 비밀값으로 복호화
          .parseClaimsJws(token);

      return true;
    } catch (Exception e) { // 복호화 과정에서 오류가 발생할 경우 false 반환
      log.warn("validToken. Token 검증 실패. token={}", token);
      return false;
    }
  }

  /**
   * 토큰 기반으로 인증 정보를 가져오는 메서드
   *
   * @param token Token
   * @return User 인증 정보
   */
  public Authentication getAuthentication(String token) {
    Claims claims = getClaims(token);
    Set<SimpleGrantedAuthority> authorities =
        Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

    return new UsernamePasswordAuthenticationToken(
        new org.springframework.security.core.userdetails.User(
            claims.getSubject(), "", authorities),
        token,
        authorities);
  }

  /**
   * Token 기반으로 User id를 가져오는 메서드
   *
   * @param token Token
   * @return (Long) user_id
   */
  public Long getUserId(String token) {
    Claims claims = getClaims(token);
    return claims.get("id", Long.class);
  }

  /**
   * Token에서 body 부분 추출
   *
   * @param token Token
   * @return Claims
   */
  private Claims getClaims(String token) {
    return Jwts.parser() // 클레임 조회
        .setSigningKey(jwtProperties.getSecretKey())
        .parseClaimsJws(token)
        .getBody();
  }
}
