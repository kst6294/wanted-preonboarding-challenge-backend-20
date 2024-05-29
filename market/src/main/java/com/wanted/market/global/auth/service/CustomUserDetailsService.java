package com.wanted.market.global.auth.service;

import com.wanted.market.domain.member.entity.Member;
import com.wanted.market.domain.member.repository.MemberRepository;
import com.wanted.market.global.auth.common.CustomUserDetails;
import com.wanted.market.global.common.code.BaseStatusCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> memberOptional = memberRepository.findByMemberIdAndStatus(username, BaseStatusCode.ACTIVE);

        Member member = memberOptional.orElseThrow(() -> new UsernameNotFoundException("Username Not Found : " + username));

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + member.getRole()));

        return new CustomUserDetails(member.getMemberId(), member.getPassword(), grantedAuthorities, member.getMemberNo());
    }
}
