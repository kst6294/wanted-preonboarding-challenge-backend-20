package io.taylor.wantedpreonboardingchallengebackend20.member.service;

import lombok.extern.slf4j.Slf4j;
import io.taylor.wantedpreonboardingchallengebackend20.common.util.JwtTokenUtil;
import io.taylor.wantedpreonboardingchallengebackend20.common.util.PasswordUtil;
import io.taylor.wantedpreonboardingchallengebackend20.member.entity.Member;
import io.taylor.wantedpreonboardingchallengebackend20.member.model.response.MemberJoinResponse;
import io.taylor.wantedpreonboardingchallengebackend20.member.model.response.MemberLoginResponse;
import io.taylor.wantedpreonboardingchallengebackend20.member.repository.MemberRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordUtil passwordUtil;
    private final JwtTokenUtil jwtTokenUtil;

    public MemberService(MemberRepository memberRepository, PasswordUtil passwordUtil, JwtTokenUtil jwtTokenUtil) {
        this.memberRepository = memberRepository;
        this.passwordUtil = passwordUtil;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public Object joinMember(Member member) {
        member.setPassword(passwordUtil.encodePassword(member.getPassword()));
        return memberRepository.save(member);
    }

    public MemberLoginResponse loginMember(Member member) {
        Member result = memberRepository.findMemberByEmail(member.getEmail());
        MemberLoginResponse response = null;
        if (result != null && passwordUtil.matchPassword(member.getPassword(), result.getPassword())) {
            response = new MemberLoginResponse(result, jwtTokenUtil.generateToken(member.getEmail()));
        }
        return response;
    }

    public Member getMemberByEmail(String email) {
        return memberRepository.findMemberByEmail(email);
    }

    public Object logoutMember(HttpHeaders headers, String str) {
        return true;
    }
}
