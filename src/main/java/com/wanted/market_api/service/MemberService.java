package com.wanted.market_api.service;

import com.wanted.market_api.dto.ApiResponse;
import com.wanted.market_api.dto.request.member.MemberLoginRequestDto;
import com.wanted.market_api.dto.request.member.MemberRegisterRequestDto;
import com.wanted.market_api.dto.response.member.MemberLoginResponseDto;

public interface MemberService {
    ApiResponse register(MemberRegisterRequestDto memberRegisterRequestDto);
    MemberLoginResponseDto login(MemberLoginRequestDto memberLoginRequestDto);
}
