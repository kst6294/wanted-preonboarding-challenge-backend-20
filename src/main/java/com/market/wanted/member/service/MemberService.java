package com.market.wanted.member.service;

import com.market.wanted.member.entity.Member;
import com.market.wanted.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;



    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElse(null);
    }
}
