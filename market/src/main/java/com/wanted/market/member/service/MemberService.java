package com.wanted.market.member.service;

import com.wanted.market.member.dto.MemberRequestDto;
import com.wanted.market.member.dto.MemberDetailResponseDto;

public interface MemberService {
    MemberDetailResponseDto join(MemberRequestDto memberRequestDto);
}
