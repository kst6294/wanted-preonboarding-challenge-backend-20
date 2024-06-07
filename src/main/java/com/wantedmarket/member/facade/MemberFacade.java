package com.wantedmarket.member.facade;

import com.wantedmarket.global.security.service.JwtService;
import com.wantedmarket.member.domain.Member;
import com.wantedmarket.member.dto.Authentication;
import com.wantedmarket.member.dto.RegisterRequest;
import com.wantedmarket.member.exception.MemberException;
import com.wantedmarket.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.wantedmarket.member.exception.MemberErrorCode.ALREADY_EXISTS;
import static com.wantedmarket.member.exception.MemberErrorCode.PASSWORD_NOT_MATCH;
import static com.wantedmarket.member.type.Role.USER;

@Component
@RequiredArgsConstructor
public class MemberFacade {

    private final MemberService memberService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Authentication.Response register(RegisterRequest request) {
        if (memberService.existMember(request.getUsername())) {
            throw new MemberException(ALREADY_EXISTS);
        }

        Member member = Member.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(USER)
                .build();
        memberService.save(member);
        String jwtToken = jwtService.generateToken(member);
        return Authentication.Response.builder()
                .token(jwtToken)
                .build();
    }

    public Authentication.Response authenticate(Authentication.Request request) {
        Member member = memberService.getMember(request.getUsername());

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new MemberException(PASSWORD_NOT_MATCH);
        }

        String jwtToken = jwtService.generateToken(member);
        return Authentication.Response.builder()
                .token(jwtToken)
                .build();
    }
}
