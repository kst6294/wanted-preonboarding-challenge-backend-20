package com.example.hs.global.security.userdetails.service;


import static com.example.hs.global.exception.ErrorCode.NOT_AUTHENTICATE_EMAIL;
import static com.example.hs.global.exception.ErrorCode.NOT_FOUND_MEMBER_LOGIN_ID;

import com.example.hs.domain.auth.entity.Member;
import com.example.hs.domain.auth.repository.MemberRepository;
import com.example.hs.domain.auth.type.Authority;
import com.example.hs.global.exception.CustomException;
import com.example.hs.global.security.userdetails.CustomUserDetails;
import com.example.hs.global.security.userdetails.MemberUserDetailsDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberUserDetailsService implements UserDetailsService {
  private final MemberRepository memberRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Member member = memberRepository.findByLoginId(username)
        .orElseThrow(() -> new CustomException(NOT_FOUND_MEMBER_LOGIN_ID));

    return new CustomUserDetails(MemberUserDetailsDomain.fromEntity(member));
  }
}
