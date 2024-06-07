package com.want.BEProject.user.controller;

import com.want.BEProject.user.dto.UserLoginRequest;
import com.want.BEProject.user.dto.UserSignupRequest;
import com.want.BEProject.user.service.UserService;
import com.want.BEProject.utils.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Log4j2
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @RequestMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@RequestBody UserSignupRequest userSignupRequest) {
        if(userSignupRequest.hasNullDataBeforeSignup(userSignupRequest)) {
            throw new RuntimeException("회원가입 정보를 확인해주세요.");
        }
        userService.signup(userSignupRequest);
    }

    @PostMapping("/login")
    public HttpStatus login(@RequestBody UserLoginRequest userLoginRequest, HttpSession session) {
        userService.login(userLoginRequest, session);

        return HttpStatus.OK;
    }

    @PutMapping("/logout")
    public void logout(HttpSession session) {
        SessionUtil.clear(session);
    }

}
