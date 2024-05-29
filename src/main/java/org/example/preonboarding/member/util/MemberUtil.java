package org.example.preonboarding.member.util;

import lombok.RequiredArgsConstructor;
import org.example.preonboarding.member.model.entity.Member;
import org.example.preonboarding.member.repository.MemberRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberUtil {
    private final MemberRepository memberRepository;

    public Member getCurrentUser() {
        String userName = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return memberRepository.findByUserId(userName).orElseThrow(() ->
                new UsernameNotFoundException("유효하지 않은 회원입니다.")
        );
    }
}
