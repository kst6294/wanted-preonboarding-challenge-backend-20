package org.example.wantedpreonboardingchallengebackend20.member.service;

import org.example.wantedpreonboardingchallengebackend20.common.jwt.JwtTokenProvider;
import org.example.wantedpreonboardingchallengebackend20.common.model.CommonResponse;
import org.example.wantedpreonboardingchallengebackend20.member.entity.Member;
import org.example.wantedpreonboardingchallengebackend20.member.model.response.MemberJoinResponse;
import org.example.wantedpreonboardingchallengebackend20.member.model.response.MemberLoginResponse;
import org.example.wantedpreonboardingchallengebackend20.member.repository.MemberRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public MemberService(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public CommonResponse<Object> joinMember(Member member) {
        Member result = memberRepository.save(member);
        if (result != null) {
            return CommonResponse.builder().result(true).data(new MemberJoinResponse(result)).build();
        }
        return CommonResponse.builder().result(false).build();
    }

    public CommonResponse<Object> loginMember(Member member) {
        Member result = memberRepository.findMemberByEmailAndPassword(member.getEmail(), member.getPassword());
        if (result != null) {
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
