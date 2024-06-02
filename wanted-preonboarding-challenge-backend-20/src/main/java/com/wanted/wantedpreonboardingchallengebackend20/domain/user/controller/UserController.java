package com.wanted.wantedpreonboardingchallengebackend20.domain.user.controller;

import com.wanted.wantedpreonboardingchallengebackend20.domain.user.dto.request.UserRequestDto;
import com.wanted.wantedpreonboardingchallengebackend20.domain.user.dto.response.SellingProductListResponseDto;
import com.wanted.wantedpreonboardingchallengebackend20.domain.user.dto.response.SuccessResponseDto;
import com.wanted.wantedpreonboardingchallengebackend20.domain.user.dto.response.UserResponseDto;
import com.wanted.wantedpreonboardingchallengebackend20.domain.user.entity.User;
import com.wanted.wantedpreonboardingchallengebackend20.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private User user;
    @PostMapping("/sign-up")
    public ResponseEntity<SuccessResponseDto> signUp(@RequestBody UserRequestDto requestDto){
        User user=userService.signUp(requestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponseDto.<User>builder()
                        .statusCode(HttpStatus.OK)
                        .resultMessage("회원가입 성공")
                        .data(user)
                        .build());
    }
    @PostMapping("/sign-in")
    public ResponseEntity<SuccessResponseDto> signIn(@RequestBody UserRequestDto requestDto){
        User user=userService.signIn(requestDto);
        if(user==null){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(SuccessResponseDto.<User>builder()
                            .statusCode(HttpStatus.UNAUTHORIZED)
                            .resultMessage("로그인 실패")
                            .build());
        }
        this.user=user;
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponseDto.<UserResponseDto>builder()
                        .statusCode(HttpStatus.OK)
                        .resultMessage("로그인 성공")
                        .data(UserResponseDto.builder()
                                .id(user.getId())
                                .userId(user.getUserId())
                                .userName(user.getName())
                                .build())
                        .build());
    }
    @GetMapping("/sign-out")
    public ResponseEntity<String> signOut(){
        user=null;
        return ResponseEntity.status(HttpStatus.OK)
                .body("로그아웃 성공");
    }

    @GetMapping("/sellinglist")
    public SellingProductListResponseDto sellingProductList(){
        User user=getLoggedInUser();
        return userService.sellingProductList(user);
    }
    public User getLoggedInUser(){
        return this.user;
    }

}
