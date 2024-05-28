package com.example.wantedmarketapi.service.impl;


import com.example.wantedmarketapi.dto.response.MemberResponseDto.*;
import com.example.wantedmarketapi.exception.GlobalErrorCode;
import com.example.wantedmarketapi.exception.custom.MemberException;
import com.example.wantedmarketapi.security.provider.JwtAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.wantedmarketapi.converter.MemberConverter;
import com.example.wantedmarketapi.service.MemberCommandService;
import com.example.wantedmarketapi.domain.member.Member;
import com.example.wantedmarketapi.dto.request.MemberRequestDto.*;
import com.example.wantedmarketapi.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final JwtAuthProvider jwtAuthProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Member signUpMember(SignUpMemberRequest request) {

        if(memberRepository.findByEmail(request.getEmail()).isPresent()){
            throw new MemberException(GlobalErrorCode.EXIST_MEMBER);
        }

        return memberRepository.save(MemberConverter.toMember(request));
    }

    @Override
    public TokenResponse login(LoginMemberRequest request) {
        Member member =
                memberRepository
                        .findByEmail(request.getEmail())
                        .orElseThrow(() -> new MemberException(GlobalErrorCode.MEMBER_NOT_FOUND));

        if (!(member.getPassword().isSamePassword(request.getPassword(), bCryptPasswordEncoder))) {
            throw new MemberException(GlobalErrorCode.PASSWORD_MISMATCH);
        }

        String accessToken = jwtAuthProvider.generateAccessToken(member.getId());
        String refreshToken = jwtAuthProvider.generateRefreshToken(member.getId());

        return MemberConverter.toLoginMemberResponse(member.getId(), accessToken, refreshToken);
    }

}
