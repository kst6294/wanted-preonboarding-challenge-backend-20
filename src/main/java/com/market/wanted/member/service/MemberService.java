package com.market.wanted.member.service;

import com.market.wanted.member.dto.SignupRequest;
import com.market.wanted.member.entity.Member;
import com.market.wanted.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElse(null);
    }

    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다."));
    }

    public void joinMember(SignupRequest request) {
        memberRepository.save(request.toEntity(passwordEncoder));
    }

}
