package com.market.wanted.security;

import com.market.wanted.member.Member;
import com.market.wanted.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findTop1ByUsername(username).orElseThrow(() -> new IllegalArgumentException("아이디가 존재하지 않습니다."));
        MemberSecurityDto memberSecurityDto = MemberSecurityDto.builder()
                .id(member.getId())
                .username(member.getUsername())
                .password(member.getPassword())
                .name(member.getName())
                .build();

        return new MemberDetails(memberSecurityDto);
    }
}
