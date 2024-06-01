package com.wanted.market.domain.member.controller;

import com.wanted.market.domain.member.entity.Member;
import com.wanted.market.domain.member.request.JoinRequest;
import com.wanted.market.domain.member.service.MemberService;
import com.wanted.market.global.common.code.BaseStatusCode;
import com.wanted.market.global.common.code.ResponseCode;
import com.wanted.market.domain.member.entity.RoleCode;
import com.wanted.market.global.common.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/join")
    public BaseResponse join(@RequestBody @Valid JoinRequest request, BindingResult result) {

        if(result.hasErrors()) {
            return new BaseResponse(ResponseCode.BAD_REQUEST);
        }

        Member member = Member.builder()
                .memberId(request.getMemberId())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(RoleCode.MEMBER)
                .status(BaseStatusCode.ACTIVE)
                .sellerTrades(new ArrayList<>())
                .buyerTrades(new ArrayList<>())
                .build();
        memberService.join(member);

        return new BaseResponse(ResponseCode.SUCCESS);
    }
}
