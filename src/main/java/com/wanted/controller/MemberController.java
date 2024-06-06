package com.wanted.controller;

import com.wanted.dto.LoginDto;
import com.wanted.dto.MemberDto;
import com.wanted.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    /* 회원 가입 */
    @PostMapping("/join/member")
    public ResponseEntity<?> join(@Valid @RequestBody MemberDto memberDto){

        MemberDto savedMember = memberService.join(memberDto);

        return ResponseEntity.ok(savedMember);
    }

    /* 로그인 */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto, HttpSession session){

        MemberDto loginedMember = memberService.login(loginDto, session);

        return ResponseEntity.ok(loginedMember);
    }

}
