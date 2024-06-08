package org.example.wantedpreonboardingchallengebackend20.common.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.example.wantedpreonboardingchallengebackend20.common.model.JwtToken;
import org.example.wantedpreonboardingchallengebackend20.member.entity.Member;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${spring.jwt.key}")
    private String secretKey;

    @Value("${spring.jwt.expiration}")
    private long expiration;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String generateToken(Member member) {
        Date accessTokenExpiresIn = new Date(new Date().getTime() + expiration);

        String accessToken = Jwts.builder()
                .subject(member.getEmail())
                .expiration(accessTokenExpiresIn)
                .issuedAt(Calendar.getInstance().getTime())
                .signWith(SignatureAlgorithm.HS256, secretKey)
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
