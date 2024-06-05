package com.challenge.market.member.service;

import com.challenge.market.member.domain.Member;
import com.challenge.market.member.repository.MemberRepository;
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
}
