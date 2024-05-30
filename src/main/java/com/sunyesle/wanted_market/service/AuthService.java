package com.sunyesle.wanted_market.service;

import com.sunyesle.wanted_market.dto.SignupRequest;
import com.sunyesle.wanted_market.dto.SignupResponse;
import com.sunyesle.wanted_market.entity.Member;
import com.sunyesle.wanted_market.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupResponse signup(SignupRequest request) {
        Member member = Member.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        memberRepository.save(member);
        return new SignupResponse(member.getId(), member.getName(), member.getEmail());
    }
}
