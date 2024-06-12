package com.example.wanted_market.controller;

import com.example.wanted_market.dto.ResponseDto;
import com.example.wanted_market.dto.request.AuthLoginRequestDto;
import com.example.wanted_market.dto.request.AuthRegisterRequestDto;
import com.example.wanted_market.exception.CommonException;
import com.example.wanted_market.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    /** 회원가입 **/
    @PostMapping("/register")
    public ResponseDto<String> register(@RequestBody AuthRegisterRequestDto authRegisterRequestDto) {
        authService.register(authRegisterRequestDto);
        return ResponseDto.ok("Registration success");

    }

    /** 로그인 **/
    @PostMapping("/login")
    public ResponseDto<String> login(@RequestBody AuthLoginRequestDto authLoginRequestDto,
                                        HttpSession session) {
        Long userId = authService.login(authLoginRequestDto);
        session.setAttribute("loginUser", userId);
        return ResponseDto.ok("Login success");
    }
}
