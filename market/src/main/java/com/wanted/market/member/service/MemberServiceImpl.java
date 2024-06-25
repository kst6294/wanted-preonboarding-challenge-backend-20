package com.wanted.market.member.service;

import com.wanted.market.member.domain.Member;
import com.wanted.market.member.dto.MemberRequestDto;
import com.wanted.market.member.dto.MemberDetailResponseDto;
import com.wanted.market.member.exception.MemberErrorCode;
import com.wanted.market.member.exception.MemberException;
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
    public MemberDetailResponseDto join(MemberRequestDto memberRequestDto) {
        // 중복 이메일 확인
        if(memberRepository.existsByEmail(memberRequestDto.getEmail())){
            log.error("email '{}' 회원가입시 이미 가입되어 있는 이메일 입력", memberRequestDto.getEmail());
            throw new MemberException(MemberErrorCode.EXISTS_MEMBER);
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(memberRequestDto.getPassword());

        // 저장
        Member savedMember = memberRepository.save(memberRequestDto.toEntity(encodedPassword));

        return MemberDetailResponseDto.createFromEntity(savedMember);
    }

}
