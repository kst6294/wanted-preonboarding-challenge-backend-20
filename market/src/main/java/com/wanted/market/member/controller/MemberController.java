package com.wanted.market.member.controller;

import com.wanted.market.member.dto.MemberRequestDto;
import com.wanted.market.member.dto.MemberDetailResponseDto;
import com.wanted.market.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/join")
    public ResponseEntity<MemberDetailResponseDto> join(@RequestBody MemberRequestDto memberRequestDto){
        MemberDetailResponseDto newMember = memberService.join(memberRequestDto);
        return ResponseEntity.ok(newMember);
    }
}
