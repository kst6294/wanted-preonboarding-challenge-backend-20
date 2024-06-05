package com.wanted.market.member.service;

import com.wanted.market.member.domain.Member;
import com.wanted.market.member.dto.MemberRequestDto;
import com.wanted.market.member.dto.MemberResponseDto;
import com.wanted.market.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    public MemberServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public MemberResponseDto join(MemberRequestDto memberRequestDto) {
        log.info("memberServiceimp={}", memberRequestDto.getRole());
        // 1. 중복 이메일 확인
        if(memberRepository.existsByEmail(memberRequestDto.getEmail())){
            throw new RuntimeException();
        }

        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(memberRequestDto.getPassword());

        // 3. 저장
        Member savedMember = memberRepository.save(memberRequestDto.toEntity(encodedPassword));

        return MemberResponseDto.createFromEntity(savedMember);
    }
}
