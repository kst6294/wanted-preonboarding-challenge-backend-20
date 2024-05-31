package com.exception_study.api.user_account.controller;

import com.exception_study.global.dto.response.ResponseDto;
import com.exception_study.api.user_account.service.UserAccountService;
import com.exception_study.api.user_account.dto.request.LoginRequest;
import com.exception_study.api.user_account.dto.response.LoginResponse;
import com.exception_study.api.user_account.dto.response.SignUpResponse;
import com.exception_study.api.user_account.dto.UserAccountDto;
import lombok.RequiredArgsConstructor;
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
    public ResponseDto<Void> signUp(@RequestBody UserAccountDto dto){
        userAccountService.signUp(dto);
        return ResponseDto.success();
    }

    @PostMapping("/login")
    public ResponseDto<LoginResponse> login(@RequestBody LoginRequest dto){
        LoginResponse response = userAccountService.login(dto);
        return ResponseDto.success(response);
    }


}
