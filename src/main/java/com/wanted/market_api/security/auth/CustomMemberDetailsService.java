package com.wanted.market_api.security.auth;

import com.wanted.market_api.constant.ErrorCode;
import com.wanted.market_api.entity.Member;
import com.wanted.market_api.exception.BaseException;
import com.wanted.market_api.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomMemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username).orElse(null);

        if(member == null)
            throw new BaseException(ErrorCode.USER_NOT_FOUND);

        return new CustomMemberDetails(member);
    }
}
