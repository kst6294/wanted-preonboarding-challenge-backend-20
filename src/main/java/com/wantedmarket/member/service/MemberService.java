package com.wantedmarket.member.service;

import com.wantedmarket.member.domain.Member;
import com.wantedmarket.member.exception.MemberException;
import com.wantedmarket.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.wantedmarket.member.exception.MemberErrorCode.NO_MEMBER;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member getMember(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new MemberException(NO_MEMBER));
    }

    public void save(Member member) {
        memberRepository.save(member);
    }

    public boolean existMember(String username) {
        return memberRepository.existsByUsername(username);
    }
}
