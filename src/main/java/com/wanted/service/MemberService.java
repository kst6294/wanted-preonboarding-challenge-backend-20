package com.wanted.service;

import com.wanted.dto.LoginDto;
import com.wanted.dto.MemberDto;
import com.wanted.entity.Member;
import com.wanted.exception.InvalidCredentialsException;
import com.wanted.exception.MemberNotFoundException;
import com.wanted.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    /* 회원 가입 */
    @Transactional
    public MemberDto join(MemberDto memberDto) {

        Member findMember = memberRepository.findByEmail(memberDto.getEmail()).orElse(null);

        if(findMember == null){
            Member member = memberDto.toEntity();
            memberRepository.save(member);
        }else{
            throw new InvalidCredentialsException("이미 존재하는 이메일 입니다.");
        }

        return memberDto;
    }

    /* 로그인 */
    public MemberDto login(LoginDto loginDto, HttpSession session) {

        Member member = memberRepository.findByEmail(loginDto.getEmail()).orElse(null);

        if(member == null){
            throw new MemberNotFoundException("존재하지 않는 회원입니다. Email 확인해주세요");
        }

        if(!member.getPassword().equals(loginDto.getPassword())){
            throw new InvalidCredentialsException("비밀번호가 틀렸습니다.");
        }

        MemberDto memberDto = new MemberDto();
        memberDto.setMember_id(member.getId());

        session.setAttribute("memberDto", memberDto);

        return memberDto;
    }
}
