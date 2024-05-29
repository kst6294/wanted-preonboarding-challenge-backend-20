package com.wanted.market.domain.member.service;

import com.wanted.market.domain.member.entity.Member;
import com.wanted.market.domain.member.repository.MemberRepository;
import com.wanted.market.global.common.code.BaseStatusCode;
import com.wanted.market.global.common.exception.DuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(rollbackFor = Exception.class)
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getMemberNo();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findAllByMemberIdAndStatus(member.getMemberId(), BaseStatusCode.ACTIVE);
        if (!findMembers.isEmpty()) {
            throw new DuplicateException("Id " + member.getMemberId() + " already exists");
        }
    }

    public Member getMember(Long memberNo) {
        return memberRepository.findByMemberNoAndStatus(memberNo, BaseStatusCode.ACTIVE).orElse(new Member());
    }
}
