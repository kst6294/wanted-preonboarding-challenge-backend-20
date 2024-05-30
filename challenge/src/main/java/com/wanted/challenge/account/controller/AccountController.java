package com.wanted.challenge.account.controller;

import com.wanted.challenge.account.request.SignupRequest;
import com.wanted.challenge.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid SignupRequest signupRequest) {
        accountService.signup(signupRequest.loginId(), signupRequest.password());

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }
}
