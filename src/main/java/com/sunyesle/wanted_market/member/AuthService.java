package com.sunyesle.wanted_market.member;

import com.sunyesle.wanted_market.member.dto.SigninRequest;
import com.sunyesle.wanted_market.member.dto.SigninResponse;
import com.sunyesle.wanted_market.member.dto.SignupRequest;
import com.sunyesle.wanted_market.member.dto.SignupResponse;
import com.sunyesle.wanted_market.global.exception.ErrorCodeException;
import com.sunyesle.wanted_market.global.exception.MemberErrorCode;
import com.sunyesle.wanted_market.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public SignupResponse signup(SignupRequest request) {
        Member member = new Member(request.getName(), request.getEmail(), request.getPassword(), passwordEncoder);
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
