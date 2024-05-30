package com.sunyesle.wanted_market.security;

import com.sunyesle.wanted_market.entity.Member;
import com.sunyesle.wanted_market.exception.ErrorCodeException;
import com.sunyesle.wanted_market.exception.MemberErrorCode;
import com.sunyesle.wanted_market.repository.MemberRepository;
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
