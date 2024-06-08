package org.example.wantedpreonboardingchallengebackend20.common.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.wantedpreonboardingchallengebackend20.common.model.JwtToken;
import org.example.wantedpreonboardingchallengebackend20.member.entity.Member;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final SecretKey key;
    private final long expiration;

    public JwtTokenProvider(@Value("${jwt.secret-key}") String secretKey, @Value("${jwt.expiration}") long expiration) {
        this.expiration = expiration;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Member member) {
        Date accessTokenExpiresIn = new Date(new Date().getTime() + expiration);

        String accessToken = Jwts.builder()
                .subject("")
                .expiration(accessTokenExpiresIn)
                .issuedAt(Calendar.getInstance().getTime())
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        return JwtToken.builder()
                .grantType("Bearer")
                .authorizationType("Authorization")
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .build()
                .getAccessToken();
    }

    public Authentication getAuthentication (String token) {
        Jwts.parser().build().parseClaimsJws(token);
        return null;
    }

    public boolean validateToken(String token) {
        return Jwts.parser().build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    public boolean getExpiration() {
        return false;
    }
}
