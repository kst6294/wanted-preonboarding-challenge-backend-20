package com.wanted.market.member.service;

import com.wanted.market.member.dto.MemberRequestDto;
import com.wanted.market.member.dto.MemberResponseDto;

public interface MemberService {
    MemberResponseDto join(MemberRequestDto memberRequestDto);
}
