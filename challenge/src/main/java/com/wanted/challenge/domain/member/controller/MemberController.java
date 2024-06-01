package com.wanted.challenge.domain.member.controller;

import com.wanted.challenge.domain.member.dto.request.EmailRequestDTO;
import com.wanted.challenge.domain.member.dto.request.LoginRequestDTO;
import com.wanted.challenge.domain.member.dto.request.SignUpRequestDTO;
import com.wanted.challenge.domain.member.dto.response.LoginResponseDTO;
import com.wanted.challenge.domain.member.service.MemberService;
import com.wanted.challenge.global.api.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    // 이메일 중복확인
    @PostMapping("email-check")
    public ResponseEntity<ApiResponse<?>> emailCheck(@Valid @RequestBody EmailRequestDTO emailRequestDTO){
        boolean result = memberService.emailCheck(emailRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.createSuccess(result, "이메일 중복 체크(true = 사용 불가, false = 사용 가능)"));
    }

    // 회원가입
    @PostMapping()
    public ResponseEntity<ApiResponse<?>> signUp(@Valid @RequestBody SignUpRequestDTO signUpRequestDTO){
        memberService.signUp(signUpRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccessNoContent("회원가입이 완료되었습니다."));
    }

    // 로그인
    @PostMapping("login")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO, HttpServletRequest request, HttpServletResponse response){
        LoginResponseDTO responseDTO = memberService.login(loginRequestDTO, request, response);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.createSuccess(responseDTO, "로그인 성공."));
    }


}
