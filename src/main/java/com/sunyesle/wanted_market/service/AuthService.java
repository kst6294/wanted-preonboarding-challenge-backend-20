package com.sunyesle.wanted_market.service;

import com.sunyesle.wanted_market.dto.SigninRequest;
import com.sunyesle.wanted_market.dto.SigninResponse;
import com.sunyesle.wanted_market.dto.SignupRequest;
import com.sunyesle.wanted_market.dto.SignupResponse;
import com.sunyesle.wanted_market.entity.Member;
import com.sunyesle.wanted_market.enums.Role;
import com.sunyesle.wanted_market.exception.ErrorCodeException;
import com.sunyesle.wanted_market.exception.MemberErrorCode;
import com.sunyesle.wanted_market.repository.MemberRepository;
import com.sunyesle.wanted_market.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public SignupResponse signup(SignupRequest request) {
        Member member = Member.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        memberRepository.save(member);
        return new SignupResponse(member.getId(), member.getName(), member.getEmail());
    }

    public SigninResponse signin(SigninRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ErrorCodeException(MemberErrorCode.MEMBER_SIGN_IN_FAILED));
        if(!passwordEncoder.matches(request.getPassword(), member.getPassword())){
            throw new ErrorCodeException(MemberErrorCode.MEMBER_SIGN_IN_FAILED);
        }
        String token = jwtTokenProvider.createToken(member.getId());
        return new SigninResponse(token);
    }
}
