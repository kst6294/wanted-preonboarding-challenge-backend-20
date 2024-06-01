package com.wanted.preonboarding.domain.user.controller;

import com.wanted.preonboarding.domain.user.dto.request.AddUserRequest;
import com.wanted.preonboarding.domain.user.dto.request.LoginUserRequest;
import com.wanted.preonboarding.domain.user.service.UserService;
import com.wanted.preonboarding.global.entity.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<ApiResponse> join(@RequestBody @Valid AddUserRequest addUserRequest) {
        userService.join(addUserRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.createSuccess("회원가입이 완료되었습니다."));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(HttpServletRequest request, @RequestBody @Valid LoginUserRequest loginUserRequest) {
        userService.login(request, loginUserRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.createSuccess("로그인이 완료되었습니다."));
    }

}
