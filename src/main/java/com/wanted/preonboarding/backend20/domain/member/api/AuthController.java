package com.wanted.preonboarding.backend20.domain.member.api;

import com.wanted.preonboarding.backend20.domain.member.application.AuthService;
import com.wanted.preonboarding.backend20.domain.member.dto.SignInDto;
import com.wanted.preonboarding.backend20.domain.member.dto.SignUpDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody @Valid SignUpDto dto) {
        authService.signUp(dto);
        return ResponseEntity.ok().body("완료");
    }

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody @Valid SignInDto dto) {
        return ResponseEntity.ok(authService.signIn(dto));
    }
}
