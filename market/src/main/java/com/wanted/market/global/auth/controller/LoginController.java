package com.wanted.market.global.auth.controller;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *  for swagger test
 */
@RestController
public class LoginController {

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest request) {
    }

    @GetMapping("/logout")
    public void logout() {

    }

    @Getter
    private class LoginRequest {
        private String id;
        private String password;
    }
}
