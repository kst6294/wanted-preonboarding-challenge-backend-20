package io.taylor.wantedpreonboardingchallengebackend20.member.service;

import io.taylor.wantedpreonboardingchallengebackend20.member.model.request.JoinRequest;
import io.taylor.wantedpreonboardingchallengebackend20.member.model.request.LoginRequest;
import io.taylor.wantedpreonboardingchallengebackend20.member.model.response.JoinResponse;
import io.taylor.wantedpreonboardingchallengebackend20.member.model.response.LoginResponse;
import lombok.extern.slf4j.Slf4j;
import io.taylor.wantedpreonboardingchallengebackend20.common.util.JwtTokenUtil;
import io.taylor.wantedpreonboardingchallengebackend20.common.util.PasswordUtil;
import io.taylor.wantedpreonboardingchallengebackend20.member.entity.Member;
import io.taylor.wantedpreonboardingchallengebackend20.member.repository.MemberRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public JoinResponse join(JoinRequest request) {
        request.setPassword(passwordUtil.encodePassword(request.getPassword()));
        Member member = memberRepository.save(new Member(request));
        if (member == null) throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "회원가입에 실패하였습니다.");

        return new JoinResponse(member);
    }

    public LoginResponse login(LoginRequest request) {
        Member member = memberRepository.findMemberByEmail(request.getEmail());
        if (member == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "회원 정보가 존재하지 않습니다.");
        if (!passwordUtil.matchPassword(request.getPassword(), member.getPassword())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "올바르지 않은 비밀번호 입니다.");

        return new LoginResponse(member, jwtTokenUtil.generateToken(request.getEmail()));
    }

    public Member getMemberByEmail(String email) {
        return memberRepository.findMemberByEmail(email);
    }

    public Object logout(HttpHeaders headers, String str) {
        return true;
    }
}
