package com.example.wanted_market.controller;

import com.example.wanted_market.dto.request.AuthRegisterRequestDto;
import com.example.wanted_market.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    /** 회원가입 **/
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRegisterRequestDto authRegisterRequestDto) {
        authService.register(authRegisterRequestDto);
        return ResponseEntity.ok("Registration success");
    }
}
