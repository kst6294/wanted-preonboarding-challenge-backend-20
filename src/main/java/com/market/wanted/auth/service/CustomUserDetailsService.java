package com.market.wanted.auth.service;

import com.market.wanted.auth.dto.CustomUserDetails;
import com.market.wanted.member.entity.Member;
import com.market.wanted.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("존재하지 않는 회원입니다."));

        if (member!= null) {
            return new CustomUserDetails(member);
        }
        return null;
    }
}
