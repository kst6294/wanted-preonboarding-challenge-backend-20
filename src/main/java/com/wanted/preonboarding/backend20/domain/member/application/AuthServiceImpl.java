package com.wanted.preonboarding.backend20.domain.member.application;

import com.wanted.preonboarding.backend20.domain.member.domain.Member;
import com.wanted.preonboarding.backend20.domain.member.dto.SignInDto;
import com.wanted.preonboarding.backend20.domain.member.dto.SignUpDto;
import com.wanted.preonboarding.backend20.domain.member.repository.MemberRepository;
import com.wanted.preonboarding.backend20.global.auth.JwtTokenProvider;
import com.wanted.preonboarding.backend20.global.exception.CustomException;
import com.wanted.preonboarding.backend20.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public void signUp(SignUpDto dto) {
        validateDuplicateEmail(dto.getEmail());
        Member member = Member.builder()
                .signUpDto(dto)
                .passwordEncoder(passwordEncoder)
                .build();
        memberRepository.save(member);
    }

    private void validateDuplicateEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
        }
    }

    @Override
    public String signIn(SignInDto dto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtTokenProvider.generateToken(authenticate);
    }
}
