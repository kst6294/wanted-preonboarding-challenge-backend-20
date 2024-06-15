package com.sunyesle.wanted_market.global.security;

import com.sunyesle.wanted_market.member.Member;
import com.sunyesle.wanted_market.global.exception.ErrorCodeException;
import com.sunyesle.wanted_market.global.exception.MemberErrorCode;
import com.sunyesle.wanted_market.member.MemberRepository;
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
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member member = memberRepository.findById(Long.parseLong(id)).orElseThrow(() -> new ErrorCodeException(MemberErrorCode.MEMBER_NOT_FOUND));
        return new CustomUserDetails(member);
    }
}
