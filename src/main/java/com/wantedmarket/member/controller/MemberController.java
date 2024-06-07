package com.wantedmarket.member.controller;

import com.wantedmarket.member.dto.Authentication;
import com.wantedmarket.member.dto.RegisterRequest;
import com.wantedmarket.member.facade.MemberFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberFacade memberFacade;

    @PostMapping("/register")
    public ResponseEntity<Authentication.Response> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(memberFacade.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Authentication.Response> authenticate(
            @RequestBody Authentication.Request request
    ) {
        return ResponseEntity.ok(memberFacade.authenticate(request));
    }
}
