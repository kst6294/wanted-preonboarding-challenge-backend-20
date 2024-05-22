package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MemberControlelr {


    @GetMapping("/api/v1/member/join")
    private ResponseEntity<?> memberJoin(){
        return ResponseEntity.ok().build();
    }

}
