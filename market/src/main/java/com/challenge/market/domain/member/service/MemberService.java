package com.challenge.market.domain.member.service;

import com.challenge.market.domain.member.constants.MemberErrorResult;
import com.challenge.market.domain.member.exception.MemberNotFoundException;
import com.challenge.market.domain.member.repository.MemberRepository;
import com.challenge.market.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;


    /**
     * 회원 가입
     *
     * @param member
     * @return
     */
    @Transactional(readOnly = false)
    public Member signUp(Member member) {
        return memberRepository.save(member);
    }

    /**
     * 회원 로그인
     *
     * @param member
     * @return
     */
    public Member signIn(Member member) {
        return memberRepository.findByName(member.getName())
                .filter( m -> member.getPw().equals(m.getPw()))
                .orElseThrow(() -> new MemberNotFoundException(MemberErrorResult.MEMBER_NOT_FOUND, member.getName()));
    }
}
