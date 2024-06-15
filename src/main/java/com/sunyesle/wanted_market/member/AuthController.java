package com.sunyesle.wanted_market.member;

import com.sunyesle.wanted_market.member.dto.SigninRequest;
import com.sunyesle.wanted_market.member.dto.SigninResponse;
import com.sunyesle.wanted_market.member.dto.SignupRequest;
import com.sunyesle.wanted_market.member.dto.SignupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest request) {
        SignupResponse response = authService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<SigninResponse> signin(@RequestBody SigninRequest request) {
        SigninResponse response = authService.signin(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
