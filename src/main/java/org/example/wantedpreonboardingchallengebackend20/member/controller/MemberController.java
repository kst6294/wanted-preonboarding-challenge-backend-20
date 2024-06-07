package org.example.wantedpreonboardingchallengebackend20.member.controller;

import ch.qos.logback.core.model.Model;
import org.example.wantedpreonboardingchallengebackend20.model.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

public class MemberController {
    @PostMapping("/members/join")
    public ResponseEntity<CommonResponse<Object>> joinMember(Model model) {
        CommonResponse<Object> response = CommonResponse.builder().result(true).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/members/login")
    public ResponseEntity<CommonResponse<Object>> loginMember(Model model) {
        CommonResponse<Object> response = CommonResponse.builder().result(true).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/members/logout")
    public ResponseEntity<CommonResponse<Object>> logoutMember(Model model) {
        CommonResponse<Object> response = CommonResponse.builder().result(true).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
