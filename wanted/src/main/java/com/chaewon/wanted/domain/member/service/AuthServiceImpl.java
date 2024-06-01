package com.chaewon.wanted.domain.member.service;

import com.chaewon.wanted.base.jwt.TokenDto;
import com.chaewon.wanted.base.jwt.TokenProvider;
import com.chaewon.wanted.base.redis.RedisUtil;
import com.chaewon.wanted.domain.member.dto.SignInDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final TokenProvider tokenProvider;
    private final RedisUtil redisUtil;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public TokenDto signIn(SignInDto signInDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(signInDto.getEmail(), signInDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // Redis에 리프레시 토큰 저장
        redisUtil.save(signInDto.getEmail(), tokenDto.getRefreshToken());

        return tokenDto;
    }

}

