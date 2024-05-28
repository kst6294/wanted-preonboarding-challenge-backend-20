package com.example.wanted.controller.user;

import com.example.wanted.controller.user.request.UserCreateRequest;
import com.example.wanted.controller.user.request.UserLoginRequest;
import com.example.wanted.controller.user.response.TokenResponse;
import com.example.wanted.controller.user.response.UserResponse;
import com.example.wanted.service.user.UserService;
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

    @GetMapping("/{id}")
    public ResponseEntity<String> getById(@PathVariable("id")  long id) {
        return ResponseEntity
                .ok()
                .body("text");
    }

    @PostMapping("/sing-up")
    public ResponseEntity<Long> create(
            @RequestBody UserCreateRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.create(request));
    }

    @PostMapping("/sing-in")
    public ResponseEntity<TokenResponse> getById(
            @RequestBody UserLoginRequest request
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
