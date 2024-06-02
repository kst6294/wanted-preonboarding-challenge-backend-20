package com.backend.market.Common.auth;

import com.backend.market.DAO.Entity.Member;
import com.backend.market.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Member member = this.memberRepository.findByLogInId(loginId).orElseThrow(
                ()->new IllegalArgumentException("error"));
        if(member == null) {
            throw new UsernameNotFoundException("User Not Found");
        }
        UserDetailsImpl userDetailsImpl = new UserDetailsImpl(member);
        return userDetailsImpl;
    }
}
