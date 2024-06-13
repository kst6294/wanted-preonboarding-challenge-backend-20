package com.challenge.market.web.member.controller;

import com.challenge.market.domain.member.exception.MemberNotFoundException;
import com.challenge.market.util.SessionUtil;
import com.challenge.market.web.member.dto.SignInRequest;
import com.challenge.market.web.member.dto.SignUpRequest;
import com.challenge.market.web.member.dto.SignUpResponse;
import com.challenge.market.domain.member.entity.Member;
import com.challenge.market.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;


    /**
     * 회원 가입
     * signUpRequest 내부에서 member 를 생성하여 리턴해 service 계층에서 도메인에만 의존하도록 한다.
     * @param signUpRequest
     * @return SignUpResponse
     */
    @PostMapping
    public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest){
        // 유효성 검사
        memberService.signUp(Member.from(signUpRequest));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/auth")
    public ResponseEntity<Void> signIn(@RequestBody SignInRequest request, HttpSession httpSession){
        Member member = memberService.signIn(Member.from(request));
        httpSession.setAttribute(SessionUtil.LOGIN_ID, member.getId());

        return ResponseEntity.ok()
                .build();
    }





}
