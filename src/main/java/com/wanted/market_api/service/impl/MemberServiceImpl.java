package com.wanted.market_api.service.impl;

import com.wanted.market_api.constant.ErrorCode;
import com.wanted.market_api.constant.Role;
import com.wanted.market_api.dto.ApiResponse;
import com.wanted.market_api.dto.request.member.MemberLoginRequestDto;
import com.wanted.market_api.dto.request.member.MemberRegisterRequestDto;
import com.wanted.market_api.dto.response.member.MemberLoginResponseDto;
import com.wanted.market_api.entity.Member;
import com.wanted.market_api.exception.BaseException;
import com.wanted.market_api.repository.MemberRepository;
import com.wanted.market_api.security.auth.CustomMemberDetails;
import com.wanted.market_api.security.jwt.JwtProvider;
import com.wanted.market_api.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    private final JwtProvider jwtProvider;

    private final PasswordEncoder passwordEncoder;


    @Override
    public ApiResponse register(MemberRegisterRequestDto memberRegisterRequestDto) {
        validateUsername(memberRegisterRequestDto.getUsername());

        memberRepository.save(
                Member.builder()
                        .username(memberRegisterRequestDto.getUsername())
                        .password(passwordEncoder.encode(memberRegisterRequestDto.getPassword()))
                        .role(Role.ROLE_MEMBER)
                        .build()
        );

        return new ApiResponse<>();
    }

    private void validateUsername(String username) {
        Member member = memberRepository.findByUsername(username).orElse(null);
        if (member != null) {
            throw new BaseException(ErrorCode.REPEATED_USER);
        }
    }
    @Override
    public MemberLoginResponseDto login(MemberLoginRequestDto memberLoginRequestDto) {
        Member member = memberRepository.findByUsername(memberLoginRequestDto.getUsername())
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(memberLoginRequestDto.getPassword(), member.getPassword())) {
            throw new BaseException(ErrorCode.INVALID_PASSWORD);
        }

        return new MemberLoginResponseDto(jwtProvider.generateToken(new CustomMemberDetails(member)));
    }

    @Override
    public Member findById(long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));
    }
}
