package com.example.wanted.common.jwt;

import com.example.wanted.domain.user.UserEntity;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {
//    private final RedisUtil redisUtil;

    @Value("${spring.jwt.secret-key}")
    private String accessKey;
    @Value("${spring.jwt.access-token-expire}")
    private Long ACCESS_TOKEN_EXPIRE_LENGTH;
    @Value("${spring.jwt.refresh-token-expire}")
    private Long REFRESH_TOKEN_EXPIRE_LENGTH;

    public String createAccessToken(UserEntity user) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_LENGTH);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, String.valueOf(accessKey))
                .claim("userId", user.getId())
                .claim("role", user.getRole())
                .claim("type", "ACCESS")
                .setIssuedAt(now)
                .setExpiration(validity)
                .compact();
    }

    public Long getUserId(String token) {
        return parseClaims(token).get("userId", Long.class);
    }

    public String getUserRole(String token) {
        return parseClaims(token).get("role", String.class);
    }

    public boolean validateToken(String token) {
        try {
            Jwts
                    .parserBuilder()
                    .setSigningKey(accessKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.error("Expried JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty", e);
        }
        return false;
    }

    public Claims parseClaims(String accessToken) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(accessKey)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

//    public String createRefreshToken(UserEntity member) {
//        redisUtil.deleteByValue(member.getId().toString());
//
//        Date now = new Date();
//        Date validity = new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_LENGTH);
//        String refreshToken =  Jwts.builder()
//                .signWith(SignatureAlgorithm.HS512, String.valueOf(jwtConfig.SECRET_KEY))
//                .claim("type", "REFRESH")
//                .setIssuedAt(now)
//                .setExpiration(validity)
//                .compact();
//        redisUtil.setRefreshToken(refreshToken, member.getId());
//        return refreshToken;
//    }
//
//    public void deleteRefreshToken(Long member_id) {
//        redisUtil.deleteByValue(member_id.toString());
//    }
}
