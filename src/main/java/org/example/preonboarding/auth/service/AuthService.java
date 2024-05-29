package org.example.preonboarding.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.preonboarding.auth.repository.AuthRepository;
import org.example.preonboarding.member.model.entity.Member;
import org.example.preonboarding.member.model.enums.Role;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("회원 인증 처리");
        Member member = authRepository.findByUserId(username).orElseThrow(() ->
                new UsernameNotFoundException("유효하지 않은 회원입니다.")
        );

        Role role = member.getRole();
        String[] roles = role.getRoleList().split(",");

        return User.builder()
                .username(String.valueOf(member.getUserId()))
                .password(member.getPassword())
                .roles(roles)
                .build();
    }

    // TODO : refreshToken 기능 추가
}
