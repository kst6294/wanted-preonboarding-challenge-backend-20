package com.market.wanted.member.controller;

import com.market.wanted.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members/")
public class MemberRestController {

    private final MemberService memberService;

    @PostMapping("/login")
    public String login(HttpSession session) {
        session.setAttribute("memberId", 1L);
        log.info("login");
        return "Ok";
    }

    @PostMapping("/login2")
    public String login2(HttpSession session) {
        session.setAttribute("memberId", 2L);
        log.info("login2");
        return "Ok";
    }



}
