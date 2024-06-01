package com.example.wanted.controller;

import com.example.wanted.model.User;
import com.example.wanted.repository.UserRepository;
import com.example.wanted.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class HomeController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        return "/index";
    }

    //로그인 폼
    @GetMapping("/login")
    @Tag(name = "User Login", description = "User Login API")
    @Operation(summary = "로그인", description = "로그인 시 사용하는 API")
    public String login() {
        return "/user/login";
    }

    //로그인  error
    @GetMapping("/login/error")
    public String login(Model model) {
        model.addAttribute("errorMsg", "홈컨트롤러 에러");
        return "/user/login";
    }

    @GetMapping("register")
    public String register() {
        return "/user/join";
    }

    @ResponseBody
    @Tag(name = "User Register", description = "User Register API")
    @Operation(summary = "회원 가입", description = "회원 가입 시 사용하는 API")
    @PostMapping("/register")
    @Parameters({
            @Parameter(name = "u_id", description = "회원번호", example = "1"),
            @Parameter(name = "email", description = "이메일", example = "hong@naver.com"),
            @Parameter(name = "password", description = "비밀번호 8자 이상", example = "abcd1234"),
            @Parameter(name = "name", description = "이름", example = "홍길동"),
            @Parameter(name = "role", description = "권한", example = "ROLE_USER"),
    })
    public String register(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return "fail";
        }
        userService.register(user);
        System.out.println("추가 성공" + user);
        return "success";
    }
}

