package com.market.wanted.member.controller;

import com.market.wanted.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members/")
public class MemberRestController {

    private final MemberService memberService;

    @PostMapping("login")
    public String login() {
        return "";

    }



}
