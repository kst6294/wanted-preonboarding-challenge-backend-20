package com.chaewon.wanted.domain.member.service;

import com.chaewon.wanted.domain.member.dto.SignUpDto;
import com.chaewon.wanted.domain.member.entity.Member;
import com.chaewon.wanted.domain.member.exception.InvalidRefreshTokenException;
import com.chaewon.wanted.domain.member.exception.MultipleLoginException;
import com.chaewon.wanted.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignUpDto signUpDto) {
        if (memberRepository.existsByEmail(signUpDto.getEmail())) {
            throw new MultipleLoginException("이미 가입되어 있는 유저입니다");
        }

        Member member = Member.createMember(signUpDto, passwordEncoder);
        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public String resolveRefreshToken(String refreshToken) {
        if (refreshToken == null || !refreshToken.startsWith("Bearer ")) {
            throw new InvalidRefreshTokenException("리프레시 토큰이 누락되었거나 올바르지 않습니다.");
        }
        return refreshToken.substring(7);
    }
}
