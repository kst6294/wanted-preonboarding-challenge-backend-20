package com.example.wanted_market.controller;

import com.example.wanted_market.dto.request.AuthLoginRequestDto;
import com.example.wanted_market.dto.request.AuthRegisterRequestDto;
import com.example.wanted_market.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
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

    /** 로그인 **/
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthLoginRequestDto authLoginRequestDto,
                                        HttpSession session) {
        try{
            Long userId = authService.login(authLoginRequestDto);
            session.setAttribute("loginUser", userId);
            return ResponseEntity.ok("Login success");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Login failed: "+e.getMessage());
        }
    }
}
