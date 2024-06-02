package com.example.wanted.user.controller;

import com.example.wanted.user.domain.UserCreate;
import com.example.wanted.user.domain.UserLogin;
import com.example.wanted.user.controller.response.TokenResponse;
import com.example.wanted.user.controller.response.UserResponse;
import com.example.wanted.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User", description = "User API")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "account, password, name으로 회원가입을 시도합니다")
    @ApiResponse(responseCode = "200", description = "회원가입 성공")
    @PostMapping("/sing-up")
    public ResponseEntity<Long> create(
            @RequestBody UserCreate request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.create(request).getId());
    }

    @Operation(summary = "로그인", description = "account, password으로 회원가입을 시도합니다")
    @ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = TokenResponse.class)))
    @PostMapping("/sing-in")
    public ResponseEntity<TokenResponse> login(
            @RequestBody UserLogin request
    ) {
        return ResponseEntity
                .ok()
                .body(TokenResponse.from(userService.login(request)));
    }

    @GetMapping("")
    @Operation(summary = "내 정보 조회", description = "내 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "유저의 정보 확인", content = @Content(schema = @Schema(implementation = UserResponse.class)))
    public ResponseEntity<UserResponse> getMyInfo(
            Principal principal
    ) {
        Long id = Long.parseLong(principal.getName());
        return ResponseEntity
                .ok()
                .body(UserResponse.from(userService.getById(id)));
    }
}
