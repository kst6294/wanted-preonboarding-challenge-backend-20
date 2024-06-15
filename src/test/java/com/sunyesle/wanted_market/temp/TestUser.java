package com.sunyesle.wanted_market.temp;

import com.sunyesle.wanted_market.global.security.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Disabled
@SpringBootTest
class TestUser {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration-time}")
    private long expirationTime;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void generatePassword() {
        System.out.println(passwordEncoder.encode("password"));
    }

    @Test
    void generateToken() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        Claims claims = Jwts.claims().setSubject("2");

        Instant now = Instant.now();
        //Instant expiryDate = now.plus(Duration.ofMillis(expirationTime));

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now))
                //.setExpiration(Date.from(expiryDate))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        System.out.println(token);
        System.out.println(jwtTokenProvider.validateToken(token));
    }
}
