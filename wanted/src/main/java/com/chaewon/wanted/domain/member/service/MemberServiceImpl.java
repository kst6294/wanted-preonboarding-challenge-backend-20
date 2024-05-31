package com.chaewon.wanted.domain.member.service;

import com.chaewon.wanted.base.jwt.TokenDto;
import com.chaewon.wanted.base.jwt.TokenProvider;
import com.chaewon.wanted.base.redis.RedisUtil;
import com.chaewon.wanted.domain.member.dto.SignInDto;
import com.chaewon.wanted.domain.member.dto.SignUpDto;
import com.chaewon.wanted.domain.member.entity.Member;
import com.chaewon.wanted.domain.member.exception.InvalidRefreshTokenException;
import com.chaewon.wanted.domain.member.exception.MultipleLoginException;
import com.chaewon.wanted.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisUtil redisUtil;

    @Transactional
    public void signup(SignUpDto signUpDto) {
        if (memberRepository.existsByEmail(signUpDto.getEmail())) {
            throw new MultipleLoginException("이미 가입되어 있는 유저입니다");
        }

        Member member = Member.createMember(signUpDto, passwordEncoder);
        memberRepository.save(member);
    }

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

    @Transactional(readOnly = true)
    public String resolveRefreshToken(String refreshToken) {
        if (refreshToken == null || !refreshToken.startsWith("Bearer ")) {
            throw new InvalidRefreshTokenException("리프레시 토큰이 누락되었거나 올바르지 않습니다.");
        }
        return refreshToken.substring(7);
    }
}
