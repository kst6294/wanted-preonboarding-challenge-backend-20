package io.taylor.wantedpreonboardingchallengebackend20.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenUtil {
    @Value("${spring.jwt.key}")
    private String secretKey;

    @Value("${spring.jwt.expiration}")
    private long expiration;

    private Key key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(String email) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + this.expiration);
        return Jwts.builder()
                .claim("email", email)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserIdFromToken(String accessToken) {
        Claims claims = getClaimsFromToken(accessToken);
        if (claims.getExpiration().before(new Date())) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "유효하지 않은 토큰입니다.");
        return claims.get("email", String.class);
    }

    public Claims getClaimsFromToken(String accessToken) {
        return Jwts.parser()
                .verifyWith((SecretKey) key)
                .build().parseSignedClaims(accessToken)
                .getPayload();
    }
}
