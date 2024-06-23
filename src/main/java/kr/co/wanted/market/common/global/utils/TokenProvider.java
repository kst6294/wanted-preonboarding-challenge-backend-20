package kr.co.wanted.market.common.global.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import kr.co.wanted.market.common.global.enums.Role;
import kr.co.wanted.market.common.global.properties.JWTProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

import static java.lang.String.format;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

    public static final String CLAIM_ROLE = "role";

    private final JWTProperties jwtProperties;


    /**
     * 토큰을 해독합니다.
     *
     * @param token 토큰
     * @return 해독 된 JWT
     * @throws TokenExpiredException    토큰 만료
     * @throws JWTVerificationException 해독 실패
     */
    public DecodedJWT parse(String token) throws JWTVerificationException {

        return JWT.require(Algorithm.HMAC512(jwtProperties.getSecret()))
                .build()
                .verify(token);
    }


    public Authentication getAuthentication(DecodedJWT decodedJWT) {

        String accessToken = decodedJWT.getToken();

        String role = decodedJWT.getClaim(CLAIM_ROLE).asString();
        if (role == null) throw new JWTVerificationException("액세스 토큰이 아님");

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(authority);

        return UsernamePasswordAuthenticationToken.authenticated(decodedJWT.getSubject(), accessToken, authorities);
    }


    public Long getSubject(String token) {

        return extractSubject(token);
    }


    public String createToken(Long id, Role role) {

        return this.createAccessToken(id, role);
    }


    private String createAccessToken(Long id, Role role) {

        return getJwtCommonBuilder(id, jwtProperties.getExpirationTime())
                .withClaim(CLAIM_ROLE, role.name())
                .sign(Algorithm.HMAC512(jwtProperties.getSecret()));
    }


    private void checkValidAccessToken(String token, boolean ignoreExpired) {

        try {
            parse(token);
        } catch (TokenExpiredException e) {
            if (!ignoreExpired) {
                throw new InsufficientAuthenticationException("토큰검증 실패 - 유효기간 만료", e);
            }
        } catch (Exception e) {
            throw new InsufficientAuthenticationException("토큰검증 실패", e);
        }

        String roleClaim = extractClaim(token);
        if (roleClaim == null) {
            throw new InsufficientAuthenticationException("액세스 토큰이 아님");
        }
    }


    private Long extractSubject(String token) {

        try {
            return Long.valueOf(JWT.decode(token).getSubject());
        } catch (JWTDecodeException e) {
            throw new InsufficientAuthenticationException(format("토큰[%s] - decode() 실패", token));
        }
    }


    private String extractClaim(String token) {

        try {
            return JWT.decode(token).getClaim(CLAIM_ROLE).asString();
        } catch (JWTDecodeException e) {
            throw new InsufficientAuthenticationException(format("토큰[%s] - getClaim(%s) 실패", token, CLAIM_ROLE));
        }
    }


    private JWTCreator.Builder getJwtCommonBuilder(Long id, long expirationTimes) {

        return JWT.create()
                .withSubject(String.valueOf(id))
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTimes));
    }

}
