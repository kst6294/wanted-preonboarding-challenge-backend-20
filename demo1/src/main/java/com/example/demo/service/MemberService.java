package com.example.demo.service;

import com.example.demo.dto.request.MemberJoin;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberService  {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean memberJoin(MemberJoin memberJoin) {

        if (memberRepository.findByEmail(memberJoin.email()).isPresent()) {
            throw new RuntimeException("중복된 아이디입니다.");
        }


        Member member = new Member(memberJoin.email(), passwordEncoder.encode(memberJoin.password()));

        return memberJoin.email().equals(memberRepository.save(member).getEmail());
    }
}
