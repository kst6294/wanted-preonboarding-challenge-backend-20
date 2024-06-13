package io.taylor.wantedpreonboardingchallengebackend20.common.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class Controller {
    @GetMapping("/")
    private ResponseEntity<Object> healthCheck() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
