package com.example.wantedmarketapi.controller;

import com.example.wantedmarketapi.common.BaseResponse;
import com.example.wantedmarketapi.converter.MemberConverter;
import com.example.wantedmarketapi.dto.request.MemberRequestDto.*;
import com.example.wantedmarketapi.dto.response.MemberResponseDto.*;
import com.example.wantedmarketapi.exception.GlobalErrorCode;
import com.example.wantedmarketapi.service.MemberCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/members")
@Tag(name = "😎 Member", description = "사용자 관련 API")
public class MemberController {

    private final MemberCommandService memberCommandService;

    @Operation(summary = "회원가입 API", description = "회원가입을 진행합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "성공"),
    })
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<SignUpMemberResponse> signUpMember(@RequestBody SignUpMemberRequest request) {
        return BaseResponse.onSuccess(
                GlobalErrorCode.CREATED,
                MemberConverter.toSignUpMemberResponse(memberCommandService.signUpMember(request)));
    }
}