package com.challenge.market.member.controller;

import com.challenge.market.member.domain.Member;
import com.challenge.market.member.dto.SignUpRequest;
import com.challenge.market.member.dto.SignUpResponse;
import com.challenge.market.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;


    /**
     * 회원 가입
     * signUpRequest 내부에서 member 를 생성하여 리턴해 service 계층에서 도메인에만 의존하도록 한다.
     * @param signUpRequest
     * @return SignUpResponse
     */
    @PostMapping
    public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest){
        // 유효성 검사
        Member member = memberService.signUp(Member.toMember(signUpRequest));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<SignUpResponse>(SignUpResponse.of(member),headers,HttpStatus.OK);

    }





}
