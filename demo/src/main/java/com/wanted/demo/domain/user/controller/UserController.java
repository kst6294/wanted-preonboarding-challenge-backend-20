package com.wanted.demo.domain.user.controller;

import com.wanted.demo.domain.user.dto.request.LoginRequestDTO;
import com.wanted.demo.domain.user.dto.request.SignUpRequestDTO;
import com.wanted.demo.domain.user.service.UserService;
import com.wanted.demo.global.util.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;
    @PostMapping("sign-up")
    public ResponseEntity<ApiResponse<?>> signUp(@Valid @RequestBody SignUpRequestDTO signUpRequestDTO){
        userService.signUp(signUpRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccessNoContent("회원가입 성공"));
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO, HttpServletRequest request){
        userService.login(loginRequestDTO,request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccessNoContent("로그인 성공"));
    }
}
