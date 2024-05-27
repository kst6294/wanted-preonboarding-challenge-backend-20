package com.example.demo.service;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> findOne = memberRepository.findByEmail(username);
        if (findOne.isPresent()) {
            Member member = findOne.get();

            return User.builder()
                    .username(member.getEmail())
                    .password(member.getPassword())
                    .roles(member.getRoles().get(0))
                    .build();
        } else {
            throw new UsernameNotFoundException("등록되지 않은 ID입니다.");
        }

    }



}