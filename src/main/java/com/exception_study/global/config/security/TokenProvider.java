package com.exception_study.global.config.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
@Slf4j
public class TokenProvider {
    @Value("${JwtSecretKey}")
    private String SECURITY_KEY;


    public String createAccessToken(String userId) {
        Date exprTime = Date.from(Instant.now().plus(30, ChronoUnit.MINUTES));

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECURITY_KEY)
                //jwt 제목, 생성일, 만료일 설정
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(exprTime)
                .compact();
    }


    public Long getExpiration(String accessToken) {
        Date expiration = Jwts.parser().setSigningKey(SECURITY_KEY).parseClaimsJws(accessToken).getBody().getExpiration();
        long now = new Date().getTime();
        return (expiration.getTime() - now);
    }

    //jwt 검증
    public String validate(String token) throws JwtException {
        Claims claims = null;
        try {
            //token을 키를 사용해서 디코딩
            claims = Jwts.parser().setSigningKey(SECURITY_KEY).parseClaimsJws(token).getBody();
        } catch (JwtException e) {
            log.info("JwtException log:{}",e.getMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            log.info("IllegalArgumentException log:{}", e.getMessage());
            throw new JwtException("IllegalArgumentException");
        }
        //디코딩된 payload에서 제목을 가져옴
        return claims.getSubject();
    }


}