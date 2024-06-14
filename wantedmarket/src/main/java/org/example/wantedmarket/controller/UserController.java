package org.example.wantedmarket.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.wantedmarket.dto.common.ApiResponse;
import org.example.wantedmarket.dto.common.DataResponse;
import org.example.wantedmarket.dto.common.ErrorResponse;
import org.example.wantedmarket.dto.user.UserJoinRequest;
import org.example.wantedmarket.exception.CustomException;
import org.example.wantedmarket.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ApiResponse joinUser(@RequestBody @Valid UserJoinRequest request) {
        // todo : valid exception 처리
        try {
            return new DataResponse<>(userService.joinUser(request));
        } catch (CustomException exception) {
            return new ErrorResponse(exception.getErrorCode(), exception.getMessage());
        }
    }

}
