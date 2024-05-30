package com.example.wanted.controller;

import com.example.wanted.model.User;
import com.example.wanted.repository.UserRepository;
import com.example.wanted.service.UserService;
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
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return "fail";
        }
        userService.register(user);
        System.out.println("추가 성공" + user);
        return "success";
    }
}

