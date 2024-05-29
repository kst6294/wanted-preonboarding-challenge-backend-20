package com.example.wantedmarketapi.service.impl;

import com.example.wantedmarketapi.domain.member.Member;
import com.example.wantedmarketapi.exception.GlobalErrorCode;
import com.example.wantedmarketapi.exception.custom.MemberException;
import com.example.wantedmarketapi.repository.MemberRepository;
import com.example.wantedmarketapi.service.MemberQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberRepository memberRepository;

    @Override
    public Member findMemberById(Long id){
        return memberRepository.findById(id).orElseThrow(() -> new MemberException(GlobalErrorCode.MEMBER_NOT_FOUND));
    }

}
