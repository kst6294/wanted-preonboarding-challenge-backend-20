package org.example.preonboarding.auth.contorller;

import lombok.RequiredArgsConstructor;
import org.example.preonboarding.auth.service.AuthService;
import org.example.preonboarding.common.enums.ResultCode;
import org.example.preonboarding.common.payload.Api;
import org.example.preonboarding.member.model.payload.request.SignupRequest;
import org.example.preonboarding.member.model.payload.response.SignupResponse;
import org.example.preonboarding.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final MemberService memberService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> createMember(@RequestBody @Validated SignupRequest signupRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Api.<SignupResponse>builder()
                        .resultCode(ResultCode.SIGNUP_SUCCESS)
                        .data(memberService.createUser(signupRequest))
                        .build()
        );
    }

}