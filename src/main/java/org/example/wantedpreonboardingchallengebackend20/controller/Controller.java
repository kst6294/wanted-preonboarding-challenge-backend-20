package org.example.wantedpreonboardingchallengebackend20.controller;

import org.example.wantedpreonboardingchallengebackend20.model.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class Controller {
    @GetMapping("/")
    private ResponseEntity<Object> healthCheck() {
        CommonResponse<Object> response = CommonResponse.builder().result(true).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
