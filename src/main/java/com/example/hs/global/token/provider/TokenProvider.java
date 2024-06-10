package com.example.hs.global.token.provider;

import static com.example.hs.global.exception.ErrorCode.JWT_EXPIRED;
import static com.example.hs.global.exception.ErrorCode.JWT_TOKEN_MALFORMED;
import static com.example.hs.global.exception.ErrorCode.JWT_TOKEN_WRONG_TYPE;
import static com.example.hs.global.exception.ErrorCode.NOT_FOUND_TOKEN;
import static com.example.hs.global.exception.ErrorCode.NO_ROLE_TOKEN;
import static com.example.hs.global.token.constant.TokenConstant.ACCESS_TOKEN_EXPIRE_TIME;
import static com.example.hs.global.token.constant.TokenConstant.AUTHORITIES_KEY;
import static com.example.hs.global.token.constant.TokenConstant.BEARER_TYPE;
import static com.example.hs.global.token.constant.TokenConstant.REFRESH_TOKEN_EXPIRE_TIME;
import static com.example.hs.global.token.constant.TokenConstant.key;

import com.example.hs.domain.auth.type.Authority;
import com.example.hs.global.exception.CustomException;
import com.example.hs.global.exception.JwtException;
import com.example.hs.global.security.userdetails.CustomUserDetails;
import com.example.hs.global.security.userdetails.MemberUserDetailsDomain;
import com.example.hs.global.token.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

  private final TokenRepository tokenRepository;

  public String generateAccessToken(String userId, Authentication authentication) {
    String authorities = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));

    long now = (new Date()).getTime();
    CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
    return Jwts.builder()
        .setId(String.valueOf(customUserDetails.getId()))
        .setSubject(userId)       // payload "sub": "name"
        .claim(AUTHORITIES_KEY, authorities)
        .setIssuedAt(new Date(now))      // payload "iat" : "현재 시간
        .setExpiration(new Date(now + ACCESS_TOKEN_EXPIRE_TIME))        // payload "exp": 151621022 (ex)
        .signWith(key, SignatureAlgorithm.HS512)    // header "alg": "HS512"
        .compact();
  }

  public String generateRefreshToken(String userId, Authentication authentication) {
    String authorities = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));

    long now = (new Date()).getTime();
    CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
    return Jwts.builder()
        .setId(String.valueOf(customUserDetails.getId()))
        .setSubject(userId)
        .claim(AUTHORITIES_KEY, authorities)
        .setIssuedAt(new Date(now))
        .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
        .signWith(key, SignatureAlgorithm.HS512)
        .compact();
  }

  public Authentication getAuthentication(String token) {

    if (token.startsWith(BEARER_TYPE)) {
      token = token.substring(BEARER_TYPE.length());
    }
    // 토큰 복호화
    Claims claims = parseClaims(token);

    if (claims.get(AUTHORITIES_KEY) == null) {
      throw new JwtException(NOT_FOUND_TOKEN);
    }

    // 클레임에서 권한 정보 가져오기
    Collection<? extends GrantedAuthority> authorities =
        Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    long id = Long.parseLong(claims.getId());
    String loginId = claims.getSubject(); // or claims.get("loginId", String.class);
    String password = ""; // Password is not used in this context but required for constructor
    Authority authority = Authority.valueOf(claims.get("auth").toString());
    MemberUserDetailsDomain userDetailsDomain = new MemberUserDetailsDomain(id, loginId, password, authority);

    // UserDetails 객체를 만들어서 Authentication 리턴
    CustomUserDetails principal = new CustomUserDetails(userDetailsDomain);
    return new UsernamePasswordAuthenticationToken(principal, "", authorities);
  }
  public String getUsername(String token) {
    return this.parseClaims(token).getSubject();
  }

  public boolean validateToken(String token) {
    Claims claims = parseClaims(token);
    return !claims.getExpiration().before(new Date());
  }

  private Claims parseClaims(String token) {
    try {
      return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    } catch (ExpiredJwtException e) {
      log.error("만료된 JWT 토큰입니다.");
      throw new JwtException(JWT_EXPIRED);
    } catch (MalformedJwtException e) {
      log.error("변조된 JWT 서명입니다.");
      throw new JwtException(JWT_TOKEN_MALFORMED);
    } catch (SignatureException | SecurityException e) {
      log.error("잘못된 JWT 서명입니다.");
      throw new JwtException(JWT_TOKEN_WRONG_TYPE);
    } catch (UnsupportedJwtException | IllegalArgumentException e) {
      log.error("지원되지 않는 JWT 토큰입니다.");
      throw new CustomException(NO_ROLE_TOKEN);
    }
  }

  public boolean checkInValidAccessToken(String userId, String token) {
    if (tokenRepository.getInvalidAccessToken(userId) != null) {
      return tokenRepository.getInvalidAccessToken(userId).equals(token);
    }
    return false;
  }
}
