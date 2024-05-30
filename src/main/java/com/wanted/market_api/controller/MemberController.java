package com.wanted.market_api.controller;

import com.wanted.market_api.dto.ApiResponse;
import com.wanted.market_api.dto.request.member.MemberLoginRequestDto;
import com.wanted.market_api.dto.request.member.MemberRegisterRequestDto;
import com.wanted.market_api.dto.response.member.MemberLoginResponseDto;
import com.wanted.market_api.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/v1/member/register")
    public ResponseEntity<ApiResponse> register(@RequestBody MemberRegisterRequestDto memberRegisterRequestDto) {
        return ResponseEntity.ok(memberService.register(memberRegisterRequestDto));
    }

    @PostMapping("/v1/member/login")
    public ResponseEntity<ApiResponse<MemberLoginResponseDto>> login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        return ResponseEntity.ok(new ApiResponse<>(memberService.login(memberLoginRequestDto)));
    }
}
