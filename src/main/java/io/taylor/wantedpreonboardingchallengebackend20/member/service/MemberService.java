package io.taylor.wantedpreonboardingchallengebackend20.member.service;

import lombok.extern.slf4j.Slf4j;
import io.taylor.wantedpreonboardingchallengebackend20.common.model.CommonResponse;
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

    public CommonResponse<Object> joinMember(Member member) {
        member.setPassword(passwordUtil.encodePassword(member.getPassword()));
        Member result = memberRepository.save(member);
        if (result != null) {
            System.out.println(result);
            return CommonResponse.builder().result(true).data(new MemberJoinResponse(result)).build();
        }
        return CommonResponse.builder().result(false).build();
    }

    public CommonResponse<Object> loginMember(Member member) {
        Member result = memberRepository.findMemberByEmail(member.getEmail());
        if (result != null && passwordUtil.matchPassword(member.getPassword(), result.getPassword())) {
            MemberLoginResponse response = new MemberLoginResponse(result, jwtTokenUtil.generateToken(member.getEmail()));
            return CommonResponse.builder().result(true).data(response).build();
        }
        return CommonResponse.builder().result(false).build();
    }

    public Member getMemberByEmail(String email) {
        return memberRepository.findMemberByEmail(email);
    }

    public CommonResponse<Object> logoutMember(HttpHeaders headers, String str) {
        return CommonResponse.builder().result(true).build();
    }
}
