package com.example.wanted.user.controller;

import com.example.wanted.user.domain.UserCreate;
import com.example.wanted.user.domain.UserLogin;
import com.example.wanted.user.controller.response.TokenResponse;
import com.example.wanted.user.controller.response.UserResponse;
import com.example.wanted.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/api/v0/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sing-up")
    public ResponseEntity<Long> create(
            @RequestBody UserCreate request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.create(request));
    }

    @PostMapping("/sing-in")
    public ResponseEntity<TokenResponse> login(
            @RequestBody UserLogin request
            ) {
        return ResponseEntity
                .ok()
                .body(TokenResponse.from(userService.login(request)));
    }

    @GetMapping("")
    public ResponseEntity<UserResponse> getMyInfo(
            Principal principal
    ) {
        Long id = Long.parseLong(principal.getName());
        return ResponseEntity
                .ok()
                .body(UserResponse.from(userService.getById(id)));
    }
}
