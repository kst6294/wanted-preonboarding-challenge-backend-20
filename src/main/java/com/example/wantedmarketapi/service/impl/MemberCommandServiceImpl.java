package com.example.wantedmarketapi.service.impl;


import com.example.wantedmarketapi.exception.GlobalErrorCode;
import com.example.wantedmarketapi.exception.custom.MemberException;
import lombok.RequiredArgsConstructor;
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

    @Override
    public Member signUpMember(SignUpMemberRequest request) {

        if(memberRepository.findByEmail(request.getEmail()).isPresent()){
            throw new MemberException(GlobalErrorCode.EXIST_MEMBER);
        }

        return memberRepository.save(MemberConverter.toMember(request));
    }

}
