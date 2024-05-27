package com.wanted.preonboarding.backend20.global.auth;

import com.wanted.preonboarding.backend20.domain.member.domain.Member;
import com.wanted.preonboarding.backend20.domain.member.domain.MemberAdapter;
import com.wanted.preonboarding.backend20.domain.member.repository.MemberRepository;
import com.wanted.preonboarding.backend20.global.exception.CustomException;
import com.wanted.preonboarding.backend20.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        return new MemberAdapter(member);
    }
}
