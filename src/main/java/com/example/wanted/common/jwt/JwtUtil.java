package com.example.wanted.common.jwt;

import com.example.wanted.user.infrastucture.UserEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {
//    private final RedisUtil redisUtil;

    private final Key key;
//    private final String accessKey;

    private Long ACCESS_TOKEN_EXPIRE_LENGTH;

    public JwtUtil(
            @Value("${spring.jwt.secret-key}") String accessKey,
            @Value("${spring.jwt.access-token-expire}") Long ACCESS_TOKEN_EXPIRE_LENGTH) {
        byte[] keyBytes = Decoders.BASE64.decode(accessKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
//        this.accessKey = accessKey;
        this.ACCESS_TOKEN_EXPIRE_LENGTH = ACCESS_TOKEN_EXPIRE_LENGTH;
    }

    public String createAccessToken(UserEntity user) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_LENGTH);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, key)
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
                    .setSigningKey(key)
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
                    .setSigningKey(key)
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
