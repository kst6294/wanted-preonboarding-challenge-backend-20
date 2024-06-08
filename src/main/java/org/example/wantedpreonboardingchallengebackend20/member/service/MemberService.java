package org.example.wantedpreonboardingchallengebackend20.member.service;

import lombok.extern.slf4j.Slf4j;
import org.example.wantedpreonboardingchallengebackend20.common.jwt.JwtTokenProvider;
import org.example.wantedpreonboardingchallengebackend20.common.model.CommonResponse;
import org.example.wantedpreonboardingchallengebackend20.member.entity.Member;
import org.example.wantedpreonboardingchallengebackend20.member.model.response.MemberJoinResponse;
import org.example.wantedpreonboardingchallengebackend20.member.model.response.MemberLoginResponse;
import org.example.wantedpreonboardingchallengebackend20.member.repository.MemberRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordService passwordService;

    public MemberService(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider, PasswordService passwordService) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordService = passwordService;
    }

    public CommonResponse<Object> joinMember(Member member) {
        member.setPassword(passwordService.encryptPassword(member.getPassword()));
        Member result = memberRepository.save(member);
        if (result != null) {
            System.out.println(result);
            return CommonResponse.builder().result(true).data(new MemberJoinResponse(result)).build();
        }
        return CommonResponse.builder().result(false).build();
    }

    public CommonResponse<Object> loginMember(Member member) {
        Member result = memberRepository.findMemberByEmail(member.getEmail());
        if (result != null && passwordService.checkPassword(member.getPassword(), result.getPassword())) {
            String jwtToken = jwtTokenProvider.generateToken(member);
            MemberLoginResponse response = new MemberLoginResponse(result, jwtToken);
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
