package com.exception_study.controller;

import com.exception_study.dto.UserAccountDto;
import com.exception_study.dto.request.LoginRequest;
import com.exception_study.dto.response.LoginResponse;
import com.exception_study.dto.response.ResponseDto;
import com.exception_study.dto.response.SignUpResponse;
import com.exception_study.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserAccountController {
    private final UserAccountService userAccountService;

    @PostMapping("/signUp")
    public ResponseDto<SignUpResponse> signUp(@RequestBody UserAccountDto dto){
        SignUpResponse response = userAccountService.signUp(dto);
        return ResponseDto.success(response);
    }

    @PostMapping("/login")
    public ResponseDto<LoginResponse> login(@RequestBody LoginRequest dto){
        LoginResponse response = userAccountService.login(dto);
        return ResponseDto.success(response);
    }

}
