package org.example.preonboarding.member.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.preonboarding.common.enums.ResultCode;
import org.example.preonboarding.common.payload.Api;
import org.example.preonboarding.member.exception.WithdrawException;
import org.example.preonboarding.member.model.payload.request.SignupRequest;
import org.example.preonboarding.member.model.payload.response.MemberResponse;
import org.example.preonboarding.member.model.payload.response.SignupResponse;
import org.example.preonboarding.member.model.payload.response.WithdrawResponse;
import org.example.preonboarding.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("")
    public ResponseEntity<?> getMembers() {
        return ResponseEntity.status(HttpStatus.OK).body(
                Api.<List<MemberResponse>>builder()
                        .resultCode(ResultCode.SUCCESS)
                        .data(memberService.getUsers())
                        .build()
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getMember(@PathVariable("userId") String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(
                Api.<MemberResponse>builder()
                        .resultCode(ResultCode.SUCCESS)
                        .data(memberService.getUserByUserId(userId))
                        .build()
        );
    }

    @PostMapping("")
    public ResponseEntity<?> createMember(@RequestBody @Validated SignupRequest signupRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Api.<SignupResponse>builder()
                        .resultCode(ResultCode.SIGNUP_SUCCESS)
                        .data(memberService.createUser(signupRequest))
                        .build()
        );
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteMember(@PathVariable("userId") String userId) throws WithdrawException {
        return ResponseEntity.status(HttpStatus.OK).body(
                Api.<WithdrawResponse>builder()
                        .resultCode(ResultCode.WITHDRAW_SUCCESS)
                        .data(memberService.deleteUser(userId))
                        .build()
        );
    }
}
