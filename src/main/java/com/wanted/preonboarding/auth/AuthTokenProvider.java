package com.wanted.preonboarding.auth;

import com.wanted.preonboarding.auth.core.AuthToken;
import com.wanted.preonboarding.auth.core.JwtAuthToken;
import com.wanted.preonboarding.auth.core.UserPrincipal;
import com.wanted.preonboarding.infra.config.jwt.JwtConfig;
import com.wanted.preonboarding.module.user.core.BaseUserInfo;
import com.wanted.preonboarding.module.user.core.UserInfo;
import com.wanted.preonboarding.module.user.service.UserFindService;
import com.wanted.preonboarding.module.utils.DateGeneratorUtil;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class AuthTokenProvider {

    private final JwtConfig jwtConfig;
    private final UserFindService userFindService;
    private Key key;

    @PostConstruct
    protected void init() {
        this.key = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());
    }

    public AuthToken createAuthToken(UserInfo userInfo, boolean isRefreshToken) {
        Date issue = DateGeneratorUtil.generateCurrentDate();
        long expirationTime = jwtConfig.getExpirationTime(isRefreshToken);
        Date expiry = new Date(issue.getTime() + expirationTime);
        return new JwtAuthToken(userInfo.getEmail(), userInfo.getMemberShip().name(), issue, expiry, key);
    }

    public Authentication getAuthentication(String token){
        AuthToken authToken = new JwtAuthToken(key, token);
        String subject = authToken.getSubject();
        BaseUserInfo userInfo = userFindService.fetchUserInfo(subject);
        UserPrincipal userPrincipal = UserPrincipal.toUserPrincipal(userInfo);
        return new UsernamePasswordAuthenticationToken(userPrincipal, userPrincipal.getPassword(), userPrincipal.getAuthorities());
    }

}
